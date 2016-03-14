package com.calendar.anthony.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
