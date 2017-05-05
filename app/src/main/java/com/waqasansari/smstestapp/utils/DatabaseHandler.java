package com.waqasansari.smstestapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.waqasansari.smstestapp.models.Employee;
import com.waqasansari.smstestapp.models.EmployeeAttendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KFMWA916 on 5/3/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SMSTest";

    private static final String TABLE_EMPLOYEE_DETAIL = "EmployeeAttendance";
    private static final String TABLE_ATTENDANCE = "Attendance";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

    private static final String COLUMN_MONTH = "month";
    private static final String COLUMN_CHECK_IN = "check_in";
    private static final String COLUMN_CHECK_OUT = "check_out";
    private static final String COLUMN_DATE = "date";

    private static List<String> getMonths() {
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

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EMPLOYEE_INFO_TABLE =
                "CREATE TABLE " + TABLE_EMPLOYEE_DETAIL +
                        "(" + COLUMN_ID + " TEXT PRIMARY KEY, " +
                        COLUMN_NAME + " TEXT);";
        db.execSQL(CREATE_EMPLOYEE_INFO_TABLE);

        String CREATE_ATTENDANCE_TABLE =
                "CREATE TABLE " + TABLE_EMPLOYEE_DETAIL +
                        "(" + COLUMN_ID + " TEXT, " +
                        COLUMN_MONTH + " TEXT, " +
                        COLUMN_CHECK_IN + " INTEGER DEFAULT 0, " +
                        COLUMN_CHECK_OUT + " INTEGER DEFAULT 0, " +
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

    public List<EmployeeAttendance> getAllEmployeesAttendance() {
        SQLiteDatabase database = getReadableDatabase();
        List<EmployeeAttendance> employeeAttendanceList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_EMPLOYEE_DETAIL;
        Cursor cursor = database.rawQuery(query, null);
        HashMap<String, String> list = new HashMap<>();
         if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.put(cursor.getString(cursor.getColumnIndex(COLUMN_ID)), cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            } while (cursor.moveToNext());
        } else return null;

        for(String month : getMonths()) {
            for(Map.Entry<String, String> entry : list.entrySet()){
                query = "SELECT * FROM " + TABLE_ATTENDANCE +
                        " WHERE " + COLUMN_ID + " = ? AND " + COLUMN_MONTH + " = ?";
                cursor = database.rawQuery(query, new String[] {entry.getKey(), month});
                List<String> checkIns = new ArrayList<>(), checkOuts = new ArrayList<>();
                if(cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    do {
                        checkIns.add(cursor.getString(cursor.getColumnIndex(COLUMN_CHECK_IN)));
                        checkOuts.add(cursor.getString(cursor.getColumnIndex(COLUMN_CHECK_OUT)));
                    } while (cursor.moveToNext());
                }
                employeeAttendanceList.add(new EmployeeAttendance(
                        entry.getKey(), entry.getValue(), cursor.getString(cursor.getColumnIndex(COLUMN_MONTH)),
                        checkIns, checkOuts
                ));
            }
        }

        cursor.close();
        database.close();

        return employeeAttendanceList;
    }

    public List<Employee> getAllEmployees() {
        SQLiteDatabase database = getReadableDatabase();
        List<Employee> employeeList= new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_EMPLOYEE_DETAIL;
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                employeeList.add(new Employee(
                        cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)))
                );
            } while (cursor.moveToNext());
        } else return null;


        cursor.close();
        database.close();
        return employeeList;
    }

    private boolean checkIfExists(String id) {
        SQLiteDatabase database = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_EMPLOYEE_DETAIL + " WHERE id = ?";

        Cursor cursor = database.rawQuery(query, new String[]{id});
        int count = cursor.getCount();
        cursor.close();
        database.close();

        return count > 0;
    }
    private boolean checkIfAlreadyCheckedIn(String id) {
        SQLiteDatabase database = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_EMPLOYEE_DETAIL + " WHERE id = ?";

        Cursor cursor = database.rawQuery(query, new String[]{id});
        int checkIn = cursor.getInt(cursor.getColumnIndex(COLUMN_CHECK_IN));
        cursor.close();
        database.close();

        return checkIn != 0;
    }

    private boolean checkIfAlreadyCheckedOut(String id) {
        SQLiteDatabase database = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_EMPLOYEE_DETAIL + " WHERE id = ?";

        Cursor cursor = database.rawQuery(query, new String[]{id});
        int checkOut = cursor.getInt(cursor.getColumnIndex(COLUMN_CHECK_OUT));
        cursor.close();
        database.close();

        return checkOut != 0;
    }

    private void addEmployee(Employee employee) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, employee.getId());
        values.put(COLUMN_NAME, employee.getName());

        database.insert(TABLE_EMPLOYEE_DETAIL, null, values);
        database.close();
    }

    public void checkInOrOut(String id) {
        if(checkIfExists(id)) {
            SQLiteDatabase database = getWritableDatabase();
            if(! checkIfAlreadyCheckedIn(id)) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_ID, id);
                values.put(COLUMN_MONTH, Utils.getCurrentMonth());
                values.put(COLUMN_DATE, Utils.getFormattedDate());
                values.put(COLUMN_CHECK_IN, 1);
                database.insert(TABLE_ATTENDANCE, null, values);
                database.close();
            } else if(! checkIfAlreadyCheckedOut(id)) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_ID, id);
                values.put(COLUMN_MONTH, Utils.getCurrentMonth());
                values.put(COLUMN_DATE, Utils.getFormattedDate());
                values.put(COLUMN_CHECK_OUT, 1);
                database.update(TABLE_ATTENDANCE, values, COLUMN_ID + " = ?", new String[]{id});
                database.close();
            }

        }

    }

}
