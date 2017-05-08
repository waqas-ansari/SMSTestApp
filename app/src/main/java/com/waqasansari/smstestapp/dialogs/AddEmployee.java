package com.waqasansari.smstestapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.waqasansari.smstestapp.R;
import com.waqasansari.smstestapp.models.Employee;
import com.waqasansari.smstestapp.utils.DatabaseHandler;

/**
 * Created by KFMWA916 on 5/8/2017.
 */

public class AddEmployee extends Dialog {
    private EditText edtName, edtPhoneNumber;

    public AddEmployee(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_employee);

        edtName = (EditText) findViewById(R.id.edtName);
        edtPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber);

        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String number = edtPhoneNumber.getText().toString();

                if(number.charAt(0) == '0')
                    number = "+92" + number.substring(1);
                else number = "+92" + number;

                DatabaseHandler handler = new DatabaseHandler(getContext());
                handler.addEmployee(new Employee(number, name));
                Toast.makeText(getContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
