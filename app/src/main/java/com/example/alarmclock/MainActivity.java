package com.example.alarmclock;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.alarmclock.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MaterialTimePicker picker;
    private Calendar calendar;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createNotificationChannel();

        binding.selectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime();

            }

        });
        binding.setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });
        binding.CancelAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });


    }


    /**
     * An Intent is a messaging object you can use to request an action from another app component.
     * there are three fundamental use cases:
     * Starting a service
     * Starting an activity
     * Delivering a broadcast(I am using this in the Set Alarm)
     **/


    private void pickTime() {
        picker = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).setHour(12).setMinute(0).setTitleText("Select Time of the Alarm ").build();
        //MaterialTimePicker.Builder() is used to create MaterialTimePicker instances.
        //MaterialTimePicker is A Dialog with a clock display and a clock face to choose the time.
        picker.show(getSupportFragmentManager(), "channel");
        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (picker.getHour() > 12) {
                    binding.selectTimeButton.setText(String.format("%02d", (picker.getHour() - 12) + "%02d", (picker.getMinute() + "PM")));
                } else if (picker.getHour() <= 12) {
                    binding.selectTimeButton.setText(String.format("%02d", (picker.getHour()) + "%02d", (picker.getMinute() + "AM")));
                }
                /**The calander will point to the time that is being selected **/
                calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
                calendar.set(Calendar.MINUTE, picker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

            }
        });
    }

    private void setAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReciever.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Finish setting an Alarm", Toast.LENGTH_SHORT).show();


    }

    private void cancelAlarm() {
        Intent intent = new Intent(this, AlarmReciever.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        /** "pendingIntent", this should be referencing to the pendingIntent in setAlarm **/
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        // For situation that if the user close the app, it would also be null;
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Canceled the alarm", Toast.LENGTH_SHORT).show();
    }

    private void createNotificationChannel() {
        //If your android is on Oreo, create this
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //fields
            CharSequence name = "Alarm clock notification";
            String description = "Wake up!";
            /**
             the NotificationManager.IMPORTANCE_HIGH provides an int that when you call the
             NotificationChannel.

             Public constructors of the NotificationChannel:
             NotificationChannel(String id, CharSequence name, int importance)
             Creates a notification channel.
             **/
            int importance = NotificationManager.IMPORTANCE_HIGH;// Priority 4 means the notification will pop up when user is using the phone
            NotificationChannel channel = new NotificationChannel("channel", name, importance);// id of the channel is same as the one in AlarmReciever.java
            channel.setDescription(description);
            /**
             * Return the handle to a system-level service by name. The class of the returned object
             varies by the requested name.https://developer.android.com/reference/android/content/Context#getSystemService(java.lang.String)
             **/

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

}