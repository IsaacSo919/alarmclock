package com.example.alarmclock;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class Alarm implements Serializable {
    private String hour;
    private String minute;
    private int id;
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(1000);


    public Alarm(String hour, String minute) {
        this.id = ID_GENERATOR.getAndIncrement();;
        this.hour = hour;
        this.minute = minute;
    }

    public Alarm() {

    }

    @Override
    public String toString() {
        return hour + " : "+ minute;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public int getId() {
        return id;
    }


}
