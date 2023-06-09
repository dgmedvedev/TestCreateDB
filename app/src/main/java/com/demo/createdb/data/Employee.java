package com.demo.createdb.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "employees")
public class Employee {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String department;
    private static int count;

    public Employee(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    @Ignore
    public Employee(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Employee.count = count;
    }
}