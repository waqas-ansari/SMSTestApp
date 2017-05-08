package com.waqasansari.smstestapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.waqasansari.smstestapp.R;
import com.waqasansari.smstestapp.models.Employee;

import java.util.List;

/**
 * Created by KFMWA916 on 5/8/2017.
 */

public class EmployeesList extends BaseAdapter {
    private Context context;
    private List<Employee> employeeList;

    public EmployeesList(Context context, List<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
    }


    @Override
    public int getCount() {
        if(employeeList != null)
            return employeeList.size();
        else return 0;
    }

    @Override
    public Object getItem(int position) {
        return employeeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_employee, parent, false);
        }
        Employee employee = (Employee) getItem(position);
        ((TextView) convertView.findViewById(R.id.txtName)).setText(employee.getName());
        ((TextView) convertView.findViewById(R.id.txtNumber)).setText(employee.getId());

        return convertView;
    }
}
