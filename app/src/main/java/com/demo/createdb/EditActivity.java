package com.demo.createdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.createdb.data.Employee;
import com.demo.createdb.data.EmployeeDatabase;
import com.demo.createdb.data.MainViewModel;

public class EditActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextDepartment;
    private Toast toastMessage;

    private SharedPreferences preferences;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        editTextName = findViewById(R.id.editTextName);
        editTextDepartment = findViewById(R.id.editTextDepartment);
        Button buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(view -> {
            String name = editTextName.getText().toString();
            String department = editTextDepartment.getText().toString();
            if (!department.isEmpty()) {
                if (name.isEmpty()) {
                    name = String.format("Employee%s", Employee.getCount());
                }
                Employee employee = new Employee(name, department);
                Employee.setCount(Employee.getCount() + 1);
                preferences.edit().putInt("count", Employee.getCount()).apply();
                viewModel.insertEmployee(employee);
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                if (toastMessage != null) {
                    toastMessage.cancel();
                }
                toastMessage = Toast.makeText(this, "Поле department должно быть заполнено", Toast.LENGTH_SHORT);
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