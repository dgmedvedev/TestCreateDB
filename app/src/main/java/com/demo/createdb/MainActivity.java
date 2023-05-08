package com.demo.createdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
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
import com.demo.createdb.data.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewEmployeees;
    private Toast toastMessage;
    private SharedPreferences preferences;
    private EmployeesAdapter adapter;

    private MainViewModel viewModel;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        recyclerViewEmployeees = findViewById(R.id.recyclerViewEmployeees);
        fab = findViewById(R.id.fab);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Employee.setCount(preferences.getInt("count", 0));

        adapter = new EmployeesAdapter();
        recyclerViewEmployeees.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEmployeees.setAdapter(adapter);

        getData();

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
                        List<Employee> employees = adapter.getEmployees();
                        if (!employees.isEmpty()) {
                            Employee employee = employees.get(viewHolder.getAdapterPosition());
                            viewModel.deleteEmployee(employee);
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

    private void getData() {
        LiveData<List<Employee>> employeesFromDB = viewModel.getEmployees();
        employeesFromDB.observe(this, employeesFromLiveData -> adapter.setEmployees(employeesFromLiveData));
    }
}