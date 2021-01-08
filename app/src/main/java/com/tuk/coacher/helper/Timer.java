package com.tuk.coacher.helper;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Timer {

    private String date_format = "dd/mm/yyyy HH:MM";
    private String travel_date, travel_time;
    private int distance;
    private int hours_to_travel;

    public Timer(String travel_date, String travel_time, int distance) {
        hours_to_travel = calulateHours(distance);
        this.travel_date = travel_date;
        this.travel_time = travel_time;
        Calendar calendar = Calendar.getInstance();
        String[] dates = travel_date.split("/");
        String[] times = travel_time.split(":");
        calendar.set(Integer.decode(dates[0]), Integer.decode(dates[1]), Integer.decode(dates[2]), Integer.decode(times[0]), Integer.decode(times[1]));
        calendar.add(Calendar.HOUR, hours_to_travel);
        setDateNtime(calendar);
    }

    private void setDateNtime(Calendar calendar) {
        int m = calendar.get(Calendar.MONTH);
        int y = calendar.get(Calendar.YEAR);
        int d = calendar.get(Calendar.DATE);
        int z = calendar.get(Calendar.DAY_OF_MONTH);
        Long timestamp = calendar.getTimeInMillis();
        Log.d("TAG",
                "Timer :: setDateNtime :: m :" + m + " y :" + y + " d :" + d + " z :" + z);
    }

    private int calulateHours(int distance) {
        //80kms per hr
        float time = distance / 80f;
        return Math.round(time);
    }

    public String getArrivalDate() {

        return this.travel_date;
    }

    public String getArrivalTime() {
        return this.travel_time;
    }

    public static Date getNow() {
        return Calendar.getInstance().getTime();
    }

    public static Long convDate2Ts(Date date) {
        return date.getTime();
    }

    public static Date convTs2Date(Long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        return cal.getTime();
    }
}
