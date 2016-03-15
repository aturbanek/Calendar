package com.calendar.anthony.calendar;

import android.content.Intent;
import android.database.Cursor;
import android.provider.CalendarContract;
import android.renderscript.Long2;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private CalendarView cv;
    private int month;
    private int day;
    private int year;
    private ArrayList<Long> eventIDs;
    private ArrayList<String> eventInfo;
    private ArrayList<Integer> intEventIDs;
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
                eventInfo = getEventInfo(eventIDs);
                intEventIDs = new ArrayList<>();
                for(int i=0; i<eventIDs.size(); i++)
                {
                    intEventIDs.add(eventIDs.get(i).intValue());
                }

               // Toast.makeText(getApplicationContext(), "" + eventInfo.get(0) + " " + eventInfo.get(1) + " " +eventIDs.get(2), Toast.LENGTH_LONG).show();

            }
        });

    }

    private ArrayList<Long> getEventIDs(int day, int month, int year) {
        ArrayList<Long> ids = new ArrayList<>();
        //calendar that is date selected in cv
        //time set to 12am
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        cal.setTimeZone(TimeZone.getTimeZone("CST"));
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.MILLISECOND,0);

        //time set to 11:59, captures all events on the specific day
        Calendar cal2 = Calendar.getInstance();
        cal2.set(year, month, day);
        cal2.setTimeZone(TimeZone.getTimeZone("CST"));
        cal2.set(Calendar.HOUR_OF_DAY,23);
        cal2.set(Calendar.MINUTE,59);
        cal2.set(Calendar.MILLISECOND,0);

        long startTime = cal.getTimeInMillis();
        long endTime = cal2.getTimeInMillis();

        //fields i will get from table
        String [] projection = new String [] {
                CalendarContract.Instances._ID,
                CalendarContract.Instances.BEGIN,
                CalendarContract.Instances.END,
                CalendarContract.Instances.EVENT_ID
        };

        Cursor cur = CalendarContract.Instances.query(getContentResolver(), projection, startTime, endTime);
        if (cur.moveToFirst()) {

            do {
                ids.add(cur.getLong(3));

            }while(cur.moveToNext());
        }
        cur.close();
        return ids;
    }

    private ArrayList<String> getEventInfo(ArrayList<Long> eventIDs) {
        ArrayList<String> info = new ArrayList<>();

        long selectedEventId=0;
        String[] projection = new String[]{
                CalendarContract.Events._ID,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION};
        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALENDAR);

        for(int i =0; i < eventIDs.size();i++ ){
            Cursor cur = getContentResolver().query(
                    CalendarContract.Events.CONTENT_URI, projection, CalendarContract.Events._ID + "= ?",
                    new String[]{Long.toString(eventIDs.get(i))}, null);
            if (cur.moveToFirst()) {

                info.add(cur.getString(3)+ "\n" + cur.getString(4));
            }
            cur.close();
        }


        return info;
    }

    public void newEvent(View view){
        Intent intent = new Intent(this, FragContNew.class);

        intent.putExtra("day",day);
        intent.putExtra("month",month);
        intent.putExtra("year",year);
        startActivity(intent);
    }

    public void viewEvents(View view){
        Intent intent = new Intent(this, FragContView.class);


        intent.putStringArrayListExtra("infoList", eventInfo);
        intent.putIntegerArrayListExtra("intIDs", intEventIDs);

        startActivity(intent);
    }

    public void deleteEvent(View view) {
        Intent intent = new Intent(this, FragViewEvent.class);

        intent.putStringArrayListExtra("events", eventInfo);
        intent.putIntegerArrayListExtra("intIDs", intEventIDs);
        startActivity(intent);
    }
}
