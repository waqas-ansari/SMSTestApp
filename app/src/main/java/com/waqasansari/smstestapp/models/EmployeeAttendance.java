package com.waqasansari.smstestapp.models;

import java.util.List;

/**
 * Created by KFMWA916 on 5/4/2017.
 */

public class EmployeeAttendance {
    private String id, name, month;
    private List<String> checkIns, checkOuts;


    public EmployeeAttendance(String id, String name, String month, List<String> checkIns, List<String> checkOuts) {
        this.id = id;
        this.name = name;
        this.month = month;
        this.checkIns = checkIns;
        this.checkOuts = checkOuts;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getCheckIns() {
        return checkIns;
    }

    public List<String> getCheckOuts() {
        return checkOuts;
    }
}
