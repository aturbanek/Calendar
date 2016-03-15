package com.calendar.anthony.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FragContView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_cont_view);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_container_view, FragViewEvent.newInstance(getIntent().getStringArrayListExtra("infoList"),
                        getIntent().getIntegerArrayListExtra("intIDs")))
                .addToBackStack(null)
                .commit();
    }
}
