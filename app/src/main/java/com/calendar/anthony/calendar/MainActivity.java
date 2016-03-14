package com.calendar.anthony.calendar;

import android.content.Intent;
import android.renderscript.Long2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CalendarView cv;
    private int month;
    private int day;
    private int year;
    private ArrayList<Long> eventIDs;
    private ArrayList<String> eventInfo;

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
                //put calls to repopulate ids and event info so that updated info can be passed to
                //the relevant fragments.
                eventIDs = getEventIDs(day, month, year);

            }
        });

    }

    private ArrayList<Long> getEventIDs(int day, int month, int year) {
        ArrayList<Long> ids = new ArrayList<>();

        return ids;
    }

    private ArrayList<String> getEventInfo(ArrayList<Long> eventIDs) {
        ArrayList<String> info = new ArrayList<>();

        return info;
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
