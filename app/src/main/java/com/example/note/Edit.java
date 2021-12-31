package com.example.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class Edit extends AppCompatActivity {

    Toolbar toolbar;
    EditText noteTiltle, noteCotent;
    Calendar c;
    String todaysDate;
    String currentTime;
    NoteDatabase db;
    Note note;
    long nID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        Intent i = getIntent();
        nID = i.getLongExtra("ID", 0);
        db = new NoteDatabase(this);
        note = db.getNote(nID);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.White));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        final String tiltle =note.getTiltle();
        String content =note.getNoidung();
        noteTiltle = findViewById(R.id.editTextTiltle);
        noteCotent= findViewById(R.id.editTextContent);
        noteTiltle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                getSupportActionBar().setTitle(tiltle);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        noteTiltle.setText(tiltle);
        noteCotent.setText(content);


        //lay ngay hien tai
        c = Calendar.getInstance();
        todaysDate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
        currentTime = pad(c.get(Calendar.HOUR_OF_DAY))+":"+pad(c.get(Calendar.MINUTE));
        Log.d("lịch", "ngày và giờ: " + todaysDate+"and" + currentTime);

    }

    private String pad(int i) {
        if(i<10)
            return "0"+i;
        return String.valueOf(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.luu) {
            if (noteTiltle.getText().length() != 0) {
                Note note = new Note(nID,noteTiltle.getText().toString(),noteCotent.getText().toString(),todaysDate,currentTime);
                Log.d("EDITED", "chinh sua: truoc khi luu id -> " + note.getID());
                NoteDatabase nDB =new NoteDatabase(getApplicationContext());
                long id =nDB.editNote(note);
                Log.d("EDITED", "EDIT: id " + id);
                goToMain();
                Toast.makeText(this, "Note Edited.", Toast.LENGTH_SHORT).show();
            }

        } else if (item.getItemId() == R.id.delete) {
            Toast.makeText(this, "canceled", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

}
