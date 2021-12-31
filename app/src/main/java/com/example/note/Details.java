package com.example.note;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Details extends AppCompatActivity {

    TextView mDetails;
Button bntOk;

    long id;
    Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDetails = findViewById(R.id.detailsOfnote);
        Intent i = getIntent();
        id = i.getLongExtra("ID", 0);

        final NoteDatabase db = new NoteDatabase(this);
        final Note note = db.getNote(id);

        getSupportActionBar().setTitle(note.getTiltle());

        mDetails.setText(note.getNoidung());
        mDetails.setMovementMethod(new ScrollingMovementMethod());


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteDatabase db = new NoteDatabase(getApplicationContext());
                db.deleteNote(id);
                Toast.makeText(getApplicationContext(), "nhan nut xoa", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                goToMain();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.editNote){
            //gui nguoi diung den edit activity
            Toast.makeText(this, "Chỉnh sửa note", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, Edit.class);
            i.putExtra("ID", id);
            startActivity(i);
        }

        if(item.getItemId()==R.id.nhacNhoNote){
            Toast.makeText(this, "Aalrm note", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, Alarm.class);
            i.putExtra("ID", id);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu,menu);
        return true;
    }
    private void goToMain() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

}
