package com.waqasansari.smstestapp.models;

/**
 * Created by KFMWA916 on 5/5/2017.
 */

public class Employee {
    private String id, name;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
