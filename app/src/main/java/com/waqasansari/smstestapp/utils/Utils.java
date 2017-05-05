package com.waqasansari.smstestapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by KFMWA916 on 5/5/2017.
 */

public class Utils {
    public static String getFormattedDate(){
        SimpleDateFormat newFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        return newFormat.format(new Date());
    }
    public static String getCurrentMonth() {
        SimpleDateFormat newFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        return newFormat.format(new Date());
    }
}
