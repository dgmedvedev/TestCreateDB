package com.demo.createdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.demo.createdb.adapters.EmployeesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Employee> employees = new ArrayList<>();

    private RecyclerView recyclerViewEmployeees;
    private Toast toastMessage;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewEmployeees = findViewById(R.id.recyclerViewEmployeees);

        employees.add(new Employee(1, "Employee1", "IT"));
        employees.add(new Employee(2, "Employee2", "FBI"));
        employees.add(new Employee(3, "Employee3", "УМП"));

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Employee.setCount(preferences.getInt("count", 0));

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
                toastMessage = Toast.makeText(MainActivity.this, "Позиция номер: " + position, Toast.LENGTH_SHORT);
                toastMessage.show();
            }

            @Override
            public void onLongClick(int position) {
                if (toastMessage != null) {
                    toastMessage.cancel();
                }
                toastMessage = Toast.makeText(MainActivity.this, "Длинная позиция номер: " + position, Toast.LENGTH_SHORT);
                toastMessage.show();
                int id = Employee.getCount();
                String name = String.format("Employee%s", id);
                Employee employee = new Employee(id, name, employees.get(position).getDepartment());
                adapter.addEmployee(employee);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        if (!employees.isEmpty()) {
                            employees.remove(viewHolder.getAdapterPosition());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
        itemTouchHelper.attachToRecyclerView(recyclerViewEmployeees);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (toastMessage != null) {
            toastMessage.cancel();
        }
        preferences.edit().putInt("count", Employee.getCount()).apply();
    }
}