package com.waqasansari.smstestapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.waqasansari.smstestapp.R;
import com.waqasansari.smstestapp.adapters.EmployeesList;
import com.waqasansari.smstestapp.dialogs.AddEmployee;
import com.waqasansari.smstestapp.models.Employee;
import com.waqasansari.smstestapp.utils.DatabaseHandler;

public class MainActivity extends AppCompatActivity {
    ListView lstEmployee;
    DatabaseHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new DatabaseHandler(this);
        lstEmployee = (ListView) findViewById(R.id.lstEmployees);

        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddEmployee(MainActivity.this).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        lstEmployee.setAdapter(new EmployeesList(this, handler.getAllEmployees()));
        lstEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Employee employee = (Employee) parent.getAdapter().getItem(position);
                Intent intent = new Intent(MainActivity.this, EmployeeReport.class);
                intent.putExtra("id", employee.getId());
                startActivity(intent);
            }
        });
    }
}
