package com.example.alarmmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.TimeZoneFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static Context context;
    private TextView dateTv;
    private TextView timeTv;
    private Button dateBtn;
    private Button timeBtn;
    private Button save;
    private RadioGroup radioGroup;
    Calendar calendar;
    Calendar newDateAndTime;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        calendar = Calendar.getInstance();
        newDateAndTime = Calendar.getInstance();
        dateTv = (TextView) findViewById(R.id.main_date_tv);
        timeTv = (TextView) findViewById(R.id.main_time_tv);
        dateBtn = (Button) findViewById(R.id.main_date_btn);
        timeBtn = (Button) findViewById(R.id.main_time_btn);
        save = (Button) findViewById(R.id.main_save);
        radioGroup = (RadioGroup) findViewById(R.id.main_RadioGroup);

        dateBtn.setOnClickListener(this);
        timeBtn.setOnClickListener(this);
        save.setOnClickListener(this);
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hourOfDay = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        switch (v.getId()) {
            case R.id.main_date_btn: {
                final SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");

                DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        newDateAndTime.set(Calendar.YEAR, year);
                        newDateAndTime.set(Calendar.MONTH, month);
                        newDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        dateTv.setText(format1.format(newDateAndTime.getTime()));
                        Log.d("testt","1 - " + formatter.format(newDateAndTime.getTime()));
                    }
                }, year, month, dayOfMonth);
                dialog.show();
                break;
            }

            case R.id.main_time_btn: {
                final SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");

                TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        newDateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        newDateAndTime.set(Calendar.MINUTE, minute);

                        timeTv.setText(format2.format(newDateAndTime.getTime()));
                        Log.d("testt","2 - " + formatter.format(newDateAndTime.getTime()));
                    }
                },hourOfDay, minute, true);
                dialog.show();
                break;
            }

            case R.id.main_save:
                Log.d("testt","current = " + formatter.format(calendar.getTime()) + " --- " + calendar.getTimeInMillis());
                Log.d("testt","new = " + formatter.format(newDateAndTime.getTime()) + " --- " + newDateAndTime.getTimeInMillis());
                if(radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(this, "Select type", Toast.LENGTH_SHORT).show();
                    break;
                }

                Log.d("testt","current = " + formatter.format(calendar.getTime()) + " --- " + calendar.getTimeInMillis());
                Log.d("testt","new = " + formatter.format(newDateAndTime.getTime()) + " --- " + newDateAndTime.getTimeInMillis());
                AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                manager.set(AlarmManager.RTC_WAKEUP,
                    newDateAndTime.getTimeInMillis(),
                    PendingIntent.getBroadcast(
                            getApplicationContext(),
                            0,
                            new Intent(IntentFilters.INTENT_FILTER_VIBRATE),
                            0
                    ));
                break;
        }
    }

    public static Context getInstance() {
        return context;
    }
}
