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
    public static ArrayList <Alarm> alarms = new ArrayList<>();

    private RecyclerView recyclerView;

    public static AlarmAdapter alarmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ArrayList for alarm objects
        // Create an alarm class
        // findViews
        recyclerView = findViewById(R.id.myRecyclerView);
        ImageView createalarm = findViewById(R.id.createalarm);
        alarmAdapter = new AlarmAdapter(alarms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(alarmAdapter);
        loadSavedAlarms();
        createalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAlarm.class);
                startActivity(intent);
            }
        });

    }


    private void saveAlarms(){
        // Get a shared preferences object
        SharedPreferences sharedPreferences = getSharedPreferences("alarms",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        for(int i = 0; i<alarms.size();i++){
            Alarm alarm = alarms.get(i);
            editor.putString(i + "_minute",alarm.getMinute());
            editor.putString(i + "_hour", alarm.getHour());
        }
        editor.apply();

    }
    private void loadSavedAlarms() {
        // Use SharedPreferences to retrieve saved alarms
        SharedPreferences sharedPreferences = getSharedPreferences("alarms", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            if (key.matches("\\d+_minute")) { // Check if the key matches the format "i_minute"
                // Extract alarm information from SharedPreferences
                String minute = sharedPreferences.getString(key, "");
                String hourKey = key.replace("_minute", "_hour"); // Get corresponding hour key
                String hour = sharedPreferences.getString(hourKey, ""); // Retrieve hour value

                // Create a new Alarm object and add it to the list
                Alarm alarm = new Alarm(hour, minute);
                alarms.add(alarm);
            }
        }

        // Notify the adapter about changes in the data
        alarmAdapter.notifyDataSetChanged();
    }

    protected void onStop(){
        super.onStop();
        saveAlarms();

    }




}