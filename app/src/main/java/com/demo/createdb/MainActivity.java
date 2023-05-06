package com.demo.createdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.demo.createdb.adapters.EmployeesAdapter;
import com.demo.createdb.data.Employee;
import com.demo.createdb.data.EmployeeDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Employee> employees = new ArrayList<>();

    private RecyclerView recyclerViewEmployeees;
    private Toast toastMessage;
    SharedPreferences preferences;
    EmployeesAdapter adapter;

    private EmployeeDatabase database;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = EmployeeDatabase.getInstance(this);

        recyclerViewEmployeees = findViewById(R.id.recyclerViewEmployeees);
        fab = findViewById(R.id.fab);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Employee.setCount(preferences.getInt("count", 0));

        adapter = new EmployeesAdapter();
        getData();

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

                Employee employee = adapter.getEmployees().get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id", employee.getId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {
//                if (toastMessage != null) {
//                    toastMessage.cancel();
//                }
//                toastMessage = Toast.makeText(MainActivity.this, "Длинная позиция номер: " + position, Toast.LENGTH_SHORT);
//                toastMessage.show();
//                int id = Employee.getCount();
//                String name = String.format("Employee%s", id);
//                Employee employee = new Employee(name, employees.get(position).getDepartment());
//                preferences.edit().putInt("count", ++id).apply();
//                Employee.setCount(id);
//                adapter.addEmployee(employee);
//                database.employeesDao().insertEmployee(employee);
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            startActivity(intent);
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
                            Employee employee = employees.get(viewHolder.getAdapterPosition());
                            employees.remove(employee);
                            adapter.removeEmployee(employee);
                            database.employeesDao().deleteEmployee(employee);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        List<Employee> employeesFromDB = database.employeesDao().getAllEmployees();
        employees.clear();
        employees.addAll(employeesFromDB);
    }
}