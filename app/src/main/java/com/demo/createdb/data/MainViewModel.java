package com.demo.createdb.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends AndroidViewModel {
    private EmployeeDatabase database;
    private LiveData<List<Employee>> employees;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = EmployeeDatabase.getInstance(getApplication());
        employees = database.employeesDao().getAllEmployees();
    }

    public LiveData<List<Employee>> getEmployees() {
        return employees;
    }

    public void insertEmployee(Employee employee) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            database.employeesDao().insertEmployee(employee);
        });
    }

    public void deleteEmployee(Employee employee) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            database.employeesDao().deleteEmployee(employee);
        });
    }

    public void deleteAllEmployees(Employee employee) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            database.employeesDao().deleteAllEmployees();
        });
    }

    public Employee getEmployeeById(int employeeId) {
        return null;
    }
}