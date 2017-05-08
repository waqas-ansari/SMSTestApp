package com.waqasansari.smstestapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by KFMWA916 on 5/5/2017.
 */

public class Utils {
    public static String getFormattedDate(){
        SimpleDateFormat newFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        return newFormat.format(new Date());
    }

    public static String getCurrentTime(){
        SimpleDateFormat newFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return newFormat.format(new Date());
    }

    public static String getCurrentMonth() {
        SimpleDateFormat newFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        return newFormat.format(new Date());
    }
    public static List<String> getMonths() {
        List<String> months = new ArrayList<>();
        for(int i = 0; i < 12; i++) {
            months.add(theMonth(i));
        }
        return months;
    }

    private static String theMonth(int month){
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }
}
