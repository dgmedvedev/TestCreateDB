package com.demo.createdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.demo.createdb.data.Employee;
import com.demo.createdb.data.MainViewModel;

public class DetailActivity extends AppCompatActivity {

    private TextView textViewId;
    private TextView textViewCount;
    private TextView textViewName;
    private TextView textViewDepartment;

    private MainViewModel viewModel;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        viewModel = new MainViewModel(getApplication());

        textViewId = findViewById(R.id.textViewId);
        textViewCount = findViewById(R.id.textViewCount);
        textViewName = findViewById(R.id.textViewName);
        textViewDepartment = findViewById(R.id.textViewDepartment);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
        } else {
            finish();
        }
        getContent();
    }

    private void getContent() {
        Employee employee = viewModel.getEmployeeById(id);
        if (employee != null) {
            textViewId.setText(Integer.toString(employee.getId()));
            textViewCount.setText(Integer.toString(Employee.getCount()));
            textViewName.setText(employee.getName());
            textViewDepartment.setText(employee.getDepartment());
        }
    }
}