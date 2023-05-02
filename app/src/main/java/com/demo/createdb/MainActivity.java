package com.demo.createdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.createdb.adapters.EmployeesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Employee> employees = new ArrayList<>();

    private TextView textViewTitle;
    private RecyclerView recyclerViewEmployeees;
    private Toast toastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTitle = findViewById(R.id.textViewTitle);
        recyclerViewEmployeees = findViewById(R.id.recyclerViewEmployeees);

        employees.add(new Employee(1, "Employee1", "IT"));
        employees.add(new Employee(2, "Employee2", "FBI"));
        employees.add(new Employee(3, "Employee3", "УМП"));

        EmployeesAdapter adapter = new EmployeesAdapter();
        adapter.setEmployees(employees);
        recyclerViewEmployeees.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEmployeees.setAdapter(adapter);

        adapter.setOnEmployeeClickListener(new EmployeesAdapter.OnEmployeeClickListener() {
            @Override
            public void onEmployeeClick(int position) {
                if (toastMessage != null) {
                    toastMessage.cancel();
                }
                toastMessage = Toast.makeText(MainActivity.this, "Позция номер: " + position, Toast.LENGTH_SHORT);
                toastMessage.show();
            }

            @Override
            public void onLongClick(int position) {
                if (toastMessage != null) {
                    toastMessage.cancel();
                }
                toastMessage = Toast.makeText(MainActivity.this, "Длинная позиция номер: " + position, Toast.LENGTH_SHORT);
                toastMessage.show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        toastMessage.cancel();
    }
}