package com.example.alarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmclock.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    /** list of alarms**/
    public static ArrayList <Alarm> alarms = new ArrayList<>();

    private static Alarm_RecyclerViewAdaptor adapter;

    private RecyclerView recyclerView;

    private static Alarm_RecyclerViewAdaptor alarmAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        // ArrayList for alarm objects
        // Create an alarm class
        // findViews
        recyclerView = findViewById(R.id.myRecyclerView);
        ImageView createalarm = findViewById(R.id.createalarm);
        alarmAdaptor = new Alarm_RecyclerViewAdaptor(alarms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(alarmAdaptor);
        createalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAlarm.class);
                startActivity(intent);
            }
        });

    }

}