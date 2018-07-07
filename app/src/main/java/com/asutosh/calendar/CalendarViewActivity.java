package com.asutosh.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashSet;

public class CalendarViewActivity extends AppCompatActivity {

    HashSet<Calendar> listSelectedDates=new HashSet<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        for (int i=1;i<=10;i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(2018, (int)(Math.random() * 100 % 12), (int)(Math.random() * 100 % 28));
            listSelectedDates.add(calendar);
        }

        CustomCalendarView custCalendarView=findViewById(R.id.custCalendarView);
        custCalendarView.enableAllDays();
        //custCalendarView.setEnabledDates(listSelectedDates)
        custCalendarView.setMinDate(Calendar.getInstance());
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.MONTH,8);
//        calendar.set(Calendar.DAY_OF_MONTH,12);
//        custCalendarView.setMaxDate(calendar);
//        custCalendarView.setDisabledDay(CustomCalendarView.Days.FRIDAY);
        HashSet<CustomCalendarView.Days> setDays=new HashSet<>();
        setDays.add(CustomCalendarView.Days.MONDAY);
        setDays.add(CustomCalendarView.Days.FRIDAY);
        custCalendarView.setDisabledDays(setDays);
        custCalendarView.setUpCalendar();

        custCalendarView.setOnDateSelectedListener(new CustomCalendarView.OnDateSelected() {
            @Override
            public void onSelected(Calendar calendar) {
                Toast.makeText(CalendarViewActivity.this,"selected---"+calendar.get(Calendar.DAY_OF_MONTH),Toast.LENGTH_LONG).show();
            }
        });
    }
}
