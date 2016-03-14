package com.calendar.anthony.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

public class MainActivity extends AppCompatActivity {

    private CalendarView cv;
    private int month;
    private int day;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cv = (CalendarView)findViewById(R.id.calendarView);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int y, int m, int dayOfMonth) {
                month = m;
                day = dayOfMonth;
                year = y;
            }
        });

    }

    public void newEvent(View view){
        Intent intent = new Intent(this, FragContNew.class);
        startActivity(intent);
    }

    public void viewEvents(View view){
        Intent intent = new Intent(this, FragContView.class);
        startActivity(intent);
    }
}
