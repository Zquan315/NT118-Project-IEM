package Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import Class.Employee;
import ex.g1.iem.Deep_Event.Info_Employee;
import ex.g1.iem.Deep_Event.Salary_Edit;
import ex.g1.iem.R;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private final List<Employee> listEmployee;

    public EmployeeAdapter(List<Employee> listEmployee) {
        this.listEmployee = listEmployee;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_employee_depart_emp, parent, false);
        return new EmployeeViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = listEmployee.get(position);
        holder.txtName.setText(employee.getName());
        holder.txtDepart.setText("Phòng ban: " + employee.getDepart());
        holder.txtRole.setText("Chức vụ: " + employee.getRole());
        holder.txtID.setText("Mã nhân viên: " + employee.getId());
        holder.imgEmployee.setImageResource(R.drawable.emp_ic);

        holder.txtName.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), Info_Employee.class);
            intent.putExtra("name", employee.getName());
            intent.putExtra("depart", employee.getDepart());
            intent.putExtra("role", employee.getRole());
            intent.putExtra("id", employee.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listEmployee.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtDepart, txtRole, txtID;
        ImageView imgEmployee;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.Name_of_employee);
            txtDepart = itemView.findViewById(R.id.depart_name);
            txtRole = itemView.findViewById(R.id.role);
            txtID = itemView.findViewById(R.id.id_Employee);
            imgEmployee = itemView.findViewById(R.id.imgEmployee);
        }
    }
}
