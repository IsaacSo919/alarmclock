package com.example.alarmclock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmclock.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    /** list of alarms**/
    public ArrayList <Alarm> alarms = new ArrayList<>();

    Alarm_RecyclerViewAdaptor alarmadadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        // ArrayList for alarm objects
        // Create an alarm class
        // findViews
        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
        ImageView createalarm = findViewById(R.id.createalarm);
        Alarm_RecyclerViewAdaptor adaptor = new Alarm_RecyclerViewAdaptor(this, alarms);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        createalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAlarm.class);
                startActivity(intent);
            }
        });

    }

}