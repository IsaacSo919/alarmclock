package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;
import static com.example.alarmclock.CreateAlarm.*;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class AlarmsDetails extends AppCompatActivity {
    private MaterialTimePicker picker;
    private Calendar calendar;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms_details);
        Intent intent = getIntent();
        if (intent != null) {
            int position = intent.getIntExtra("alarm_position", -1);
            if (position != -1) {
                Alarm alarm = MainActivity.alarms.get(position);
                // Set details
                TextView datetextview = findViewById(R.id.editselectedTime);
                datetextview.setText(alarm.toString());
            }
        }
        Button cancelbutton = findViewById(R.id.editcancelButton);
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Button selecttimed = findViewById(R.id.editselectTimeButton);
        selecttimed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        Button saveeditedalarm = findViewById(R.id.editsaveButton);
        saveeditedalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAlarm();
            }
        });
    }

    public void showTimePicker() {
        picker = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).setHour(12).setMinute(0).setTitleText("Select Time").build();
        //MaterialTimePicker.Builder() is used to create MaterialTimePicker instances.
        //MaterialTimePicker is A Dialog with a clock display and a clock face to choose the time.
        picker.show(getSupportFragmentManager(), "channel");
        TextView selectedTime = findViewById(R.id.editselectedTime);
        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (picker.getHour() > 12) {
//                    String hour = String.valueOf(picker.getHour());
//                    String min = String.valueOf(picker.getMinute());
                    selectedTime.setText(
                            String.format("%02d", (picker.getHour() - 12)) + " : " + String.format("%02d", picker.getMinute()) + " PM");
                } else if (picker.getHour() == 12) {
                    selectedTime.setText(
                            String.format("%02d", (picker.getHour())) + " : " + String.format("%02d", picker.getMinute()) + " PM");

                } else {

                    selectedTime.setText(picker.getHour() + ":" + picker.getMinute() + "AM");

                }
                /**The calander will point to the time that is being selected **/
                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
                calendar.set(Calendar.MINUTE, picker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

            }
        });
    }

    private void saveAlarm() {
        if (calendar != null) {
            //Gettting hour and minute from calendar class
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            String hourString = String.format("%02d", hour);
            String minuteString = String.format("%02d", minute);

            // Retrieve the position of the alarm being edited
            int position = getIntent().getIntExtra("alarm_position", -1);
            if (position != -1) {
                // Obtain the position of the alarm to be edited
                Alarm editedAlarm = MainActivity.alarms.get(position);

                // Update the attributes of the existing alarm
                editedAlarm.setHour(hourString);
                editedAlarm.setMinute(minuteString);

                // Update the alarm in SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("alarm", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String alarmKey = "alarm_" + System.currentTimeMillis();
                editor.putString(alarmKey + "_hour_", hourString);
                editor.putString(alarmKey + "_minute", minuteString);
                editor.apply();

                // Create a new Intent for AlarmReceiver
                Intent intent = new Intent(this, AlarmReciever.class);

                // Create a PendingIntent with the existing alarm's position
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, position, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // Get the AlarmManager instance
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                // Cancel the existing alarm
                alarmManager.cancel(pendingIntent);

                // Set the new alarm with updated time
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

                // Notify MainActivity of the change
                Intent resultIntent = new Intent();
                resultIntent.putExtra("editedAlarm", editedAlarm);
                setResult(RESULT_OK, resultIntent);

                // Finish activity
                onBackPressed();
            }
        }
    }



    private void createNotificationChannel() {
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();
        System.out.println(timeInMillis);
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