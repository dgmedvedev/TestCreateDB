package com.demo.createdb.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.createdb.data.Employee;
import com.demo.createdb.R;

import java.util.ArrayList;
import java.util.List;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.EmployeesViewHolder> {

    private List<Employee> employees;
    private OnEmployeeClickListener onEmployeeClickListener;

    public EmployeesAdapter() {
        this.employees = new ArrayList<>();
    }

    public interface OnEmployeeClickListener {
        void onEmployeeClick(int position);

        void onLongClick(int position);
    }

    @NonNull
    @Override
    public EmployeesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_item, parent, false);
        return new EmployeesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeesViewHolder holder, int position) {
        Employee employee = employees.get(position);
        String name = employee.getName();
        String department = employee.getDepartment();
        String result = String.format("%s : %s", name, department);
        holder.textViewEmployeeItem.setText(result);
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class EmployeesViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewEmployeeItem;

        public EmployeesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEmployeeItem = itemView.findViewById(R.id.textViewEmployeeItem);
            itemView.setOnClickListener(view -> {
                if (onEmployeeClickListener != null) {
                    onEmployeeClickListener.onEmployeeClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(view -> {
                if (onEmployeeClickListener != null) {
                    onEmployeeClickListener.onLongClick(getAdapterPosition());
                }
                return true;
            });
        }
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
        notifyDataSetChanged();
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        notifyDataSetChanged();
    }

    public void setOnEmployeeClickListener(OnEmployeeClickListener onEmployeeClickListener) {
        this.onEmployeeClickListener = onEmployeeClickListener;
    }
}