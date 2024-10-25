package Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Class.SalaryManagement;
import ex.g1.iem.Deep_Event.Salary_Edit;
import ex.g1.iem.R;


public class SalaryAdapter extends RecyclerView.Adapter<SalaryAdapter.SalaryViewHolder> {

    private List<SalaryManagement> listSalary;

    public SalaryAdapter(List<SalaryManagement> listSalary) {
        this.listSalary = listSalary;
    }

    @NonNull
    @Override
    public SalaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_salary, parent, false);
        return new SalaryViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SalaryViewHolder holder, int position) {
        SalaryManagement employee = listSalary.get(position);
        holder.txtName.setText(employee.getName());
        holder.txtBasicSalary.setText("Lương cơ bản: " + employee.getBasicSalary() + " VND");
        holder.txtLeaveDays.setText("Số ngày nghỉ: " + employee.getLeaveDays());
        holder.txtTotalSalary.setText("Lương tổng kết: " + employee.getTotalSalary() + " VND");
        holder.imgEmployee.setImageResource(R.drawable.emp_ic);

        holder.itemView.setOnClickListener(v -> {
            // Gửi tất cả dữ liệu sang Salary_Edit
            Intent intent = new Intent(v.getContext(), Salary_Edit.class);
            intent.putExtra("employee_name", employee.getName());
            intent.putExtra("employee_basicSalary", employee.getBasicSalary());
            intent.putExtra("employee_leaveDays", Integer.toString(employee.getLeaveDays()));
            v.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return listSalary.size();
    }

    public static class SalaryViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtBasicSalary, txtLeaveDays, txtTotalSalary;
        ImageView imgEmployee;

        public SalaryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtBasicSalary = itemView.findViewById(R.id.txtBasicSalary);
            txtLeaveDays = itemView.findViewById(R.id.txtLeaveDays);
            txtTotalSalary = itemView.findViewById(R.id.txtTotalSalary);
            imgEmployee = itemView.findViewById(R.id.imgEmployee);
        }
    }
}
