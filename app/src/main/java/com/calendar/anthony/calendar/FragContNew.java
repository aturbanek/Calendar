package com.calendar.anthony.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FragContNew extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_cont_new);


        getFragmentManager()
                .beginTransaction()
                .replace(R.id.new_event_container, FragNewEvent.newInstance(getIntent().getIntExtra("day", 13),
                        getIntent().getIntExtra("month", 2),
                        getIntent().getIntExtra("year", 2016)))
                .addToBackStack(null)
                .commit();
    }
}
