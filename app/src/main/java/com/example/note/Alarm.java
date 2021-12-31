package com.example.note;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Alarm extends AppCompatActivity {
    Button bntTime, bntOKAlarm;
    TextView thoigianHG;
   long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm2);

        Intent i = getIntent();
        id = i.getLongExtra("ID", 0);

        final NoteDatabase db = new NoteDatabase(this);
        final Note note = db.getNote(id);
        bntOKAlarm = findViewById(R.id.OKAlarm);
        thoigianHG = findViewById(R.id.textViewTime);
        bntTime = findViewById(R.id.bntTime);

        bntTime.setOnClickListener(new View.OnClickListener() {
            final Calendar newCalender = Calendar.getInstance();
            @Override
            public void onClick(View v) {

            DatePickerDialog dialog = new DatePickerDialog(Alarm.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                    final Calendar newDate = Calendar.getInstance();
                    Calendar newTime = Calendar.getInstance();
                    TimePickerDialog time = new TimePickerDialog(Alarm.this, new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            newDate.set(year,month,dayOfMonth,hourOfDay,minute,0);
                            Calendar tem = Calendar.getInstance();
                            Log.w("TIME",System.currentTimeMillis()+"");
//                                if(newDate.getTimeInMillis()-tem.getTimeInMillis()>0)//rang buoc thoi gian hg phai o tuong lai
                            thoigianHG.setText(newDate.getTime().toString());
//                                else
//                                    Toast.makeText(MainPage.this,"Invalid time",Toast.LENGTH_SHORT).show();

                        }
                    },newTime.get(Calendar.HOUR_OF_DAY),newTime.get(Calendar.MINUTE),true);
                    time.show();

                }
            },newCalender.get(Calendar.YEAR),newCalender.get(Calendar.MONTH),newCalender.get(Calendar.DAY_OF_MONTH));

            dialog.getDatePicker().setMinDate(System.currentTimeMillis());
            dialog.show();
        }
        });

        bntOKAlarm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if(thoigianHG.getText().length() != 0){

                    Date remind = new Date(thoigianHG.getText().toString().trim());
                    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
                    calendar.setTime(remind);
                    calendar.set(Calendar.SECOND, 0);

                    Intent intent = new Intent(Alarm.this, NotifierAlarm.class);//truyen du lieu
                    intent.putExtra("Tiltle", note.getTiltle());//them qua input

                    intent.putExtra("id", note.getID());
                    //tao pendingIntent
                    PendingIntent intent1 = PendingIntent.getBroadcast(Alarm.this, (int) note.getID(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), intent1);

                    Toast.makeText(Alarm.this, "Alarm Successfully", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }else{
                    Toast.makeText(Alarm.this, "Time ?", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToMain() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

}
