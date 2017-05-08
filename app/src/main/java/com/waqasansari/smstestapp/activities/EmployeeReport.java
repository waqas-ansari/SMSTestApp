package com.waqasansari.smstestapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.waqasansari.smstestapp.R;
import com.waqasansari.smstestapp.adapters.AttendanceList;
import com.waqasansari.smstestapp.models.EmployeeAttendance;
import com.waqasansari.smstestapp.utils.DatabaseHandler;
import com.waqasansari.smstestapp.utils.Utils;

public class EmployeeReport extends AppCompatActivity {
    DatabaseHandler handler;
    String userId = "";

    TextView txtName, txtNumber;
    ListView lstDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_report);

        txtName = (TextView) findViewById(R.id.txtName);
        txtNumber = (TextView) findViewById(R.id.txtNumber);
        lstDetail = (ListView) findViewById(R.id.lstDetail);

        if(getIntent().getExtras() != null) {
            userId = getIntent().getStringExtra("id");

        }

        Spinner spinner = (Spinner) findViewById(R.id.spnMonth);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Utils.getMonths()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String month = (String) parent.getAdapter().getItem(position);
                handler = new DatabaseHandler(EmployeeReport.this);
                EmployeeAttendance attendance = handler.getAttendance(userId, month);
                setViews(attendance);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setViews(EmployeeAttendance attendance) {
        txtName.setText(attendance.getName());
        txtNumber.setText(attendance.getId());

        lstDetail.setAdapter(new AttendanceList(this, attendance.getCheckIns(), attendance.getCheckOuts(), attendance.getDateList()));
    }

}
