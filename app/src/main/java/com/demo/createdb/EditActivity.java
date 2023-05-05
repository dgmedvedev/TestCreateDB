package com.demo.createdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.createdb.data.Employee;
import com.demo.createdb.data.EmployeeDatabase;

public class EditActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextDepartment;
    private Toast toastMessage;

    private EmployeeDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        database = EmployeeDatabase.getInstance(this);

        editTextName = findViewById(R.id.editTextName);
        editTextDepartment = findViewById(R.id.editTextDepartment);
        Button buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(view -> {
            String name = editTextName.getText().toString();
            String department = editTextDepartment.getText().toString();
            if (!name.isEmpty() && !department.isEmpty()) {
                Employee employee = new Employee(name, department);
                Employee.setCount(Employee.getCount() + 1);
                database.employeesDao().insertEmployee(employee);
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                if (toastMessage != null) {
                    toastMessage.cancel();
                }
                toastMessage = Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT);
                toastMessage.show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (toastMessage != null) {
            toastMessage.cancel();
        }
    }
}