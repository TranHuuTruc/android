package com.example.note;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class AddNote extends AppCompatActivity {

    TextView textView;
    Button bntHenGio;
    Toolbar toolbar;
    EditText noteTiltle, noteDetail;
    Calendar c;
    String todaysDate;
    String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//nút quay lại


        noteTiltle = findViewById(R.id.editTextTiltle);
        noteDetail = findViewById(R.id.editTextContent);
        noteTiltle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

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
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        if (item.getItemId() == R.id.luu) {
            if (noteTiltle.getText().length() != 0) {


                //them note
                Note note = new Note(noteTiltle.getText().toString(), noteDetail.getText().toString(), todaysDate, currentTime);
                NoteDatabase sDB = new NoteDatabase(this);
                long id = sDB.addNote(note);//them note vao database sqlite
                Note check = sDB.getNote(id);

                Log.d("Them", "Note: " + id + "-> tiltle: " + check.getTiltle() + " Date " + check.getDate());
                onBackPressed();

                Toast.makeText(this, "đã lưu", Toast.LENGTH_SHORT).show();

            } else {
                noteTiltle.setError(" tiltle không thể thiếu");
            }

        } else if (item.getItemId() == R.id.delete) {
            Toast.makeText(this, "canceled", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
        @Override
        public void onBackPressed () {
            super.onBackPressed();
        }
}
