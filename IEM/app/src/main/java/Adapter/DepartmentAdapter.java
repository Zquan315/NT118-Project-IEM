package Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import Class.Department;
import ex.g1.iem.R;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.DepartmentViewHolder> {

    private final List<Department> listDepartment;

    public DepartmentAdapter(List<Department> listDepartment) {
        this.listDepartment = listDepartment;
    }

    @NonNull
    @Override
    public DepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_department, parent, false);
        return new DepartmentViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DepartmentViewHolder holder, int position) {
        Department department = listDepartment.get(position);
        holder.txtName.setText(department.getName());
        holder.txtHeader.setText("Trưởng phòng ban: " + department.getHeader());
        holder.txtAmountEmployee.setText("Số lượng nhân viên: " + department.getAmount_employee());
        holder.txtAmountProject.setText("Số lượng dự án: " + department.getAmount_project());
        holder.imgDepartment.setImageResource(department.getImageResource());
    }

    @Override
    public int getItemCount() {
        return listDepartment.size();
    }

    public static class DepartmentViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtHeader, txtAmountEmployee, txtAmountProject;
        ImageView imgDepartment;

        public DepartmentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.Name_of_department);
            txtHeader = itemView.findViewById(R.id.header_department);
            txtAmountEmployee = itemView.findViewById(R.id.amount_employee);
            txtAmountProject = itemView.findViewById(R.id.amount_project);
            imgDepartment = itemView.findViewById(R.id.imgDepartment);
        }
    }
}
