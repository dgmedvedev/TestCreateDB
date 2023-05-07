package com.demo.createdb.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EmployeesDao {
    @Query("SELECT*FROM employees")
    LiveData<List<Employee>> getAllEmployees();

    @Query("SELECT*FROM employees WHERE id == :employeeId")
    Employee getEmployeeById(int employeeId);

    @Insert
    void insertEmployee(Employee employee);

    @Delete
    void deleteEmployee(Employee employee);

    @Query("DELETE FROM employees")
    void deleteAllEmployees();
}