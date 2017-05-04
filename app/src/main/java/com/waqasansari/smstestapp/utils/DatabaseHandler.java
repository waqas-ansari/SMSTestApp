package com.waqasansari.smstestapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KFMWA916 on 5/3/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SMSTest";

    private static final String TABLE_EMPLOYEE_DETAIL = "Employee";
    private static final String TABLE_ATTENDANCE = "Attendance";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

    private static final String COLUMN_CHECK_IN = "check_in";
    private static final String COLUMN_CHECK_OUT = "check_out";
    private static final String COLUMN_DATE = "date";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EMPLOYEE_INFO_TABLE =
                "CREATE TABLE " + TABLE_EMPLOYEE_DETAIL +
                        "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME + " TEXT);";
        db.execSQL(CREATE_EMPLOYEE_INFO_TABLE);

        String CREATE_ATTENDANCE_TABLE =
                "CREATE TABLE " + TABLE_EMPLOYEE_DETAIL +
                        "(" + COLUMN_ID + " INTEGER, " +
                        COLUMN_CHECK_IN + " TEXT, " +
                        COLUMN_CHECK_OUT + " TEXT, " +
                        COLUMN_DATE + " TEXT);";
        db.execSQL(CREATE_ATTENDANCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);

        // Create tables again
        onCreate(db);
    }


}
