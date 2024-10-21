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
import Class.ProjectManage;
import ex.g1.iem.Deep_Event.Info_Project;
import ex.g1.iem.R;

public class ProjectManageAdapter extends RecyclerView.Adapter<ProjectManageAdapter.ProjectManageViewHolder> {

    private final List<ProjectManage> listProjectManage;

    public ProjectManageAdapter(List<ProjectManage> listProjectManage) {
        this.listProjectManage = listProjectManage;
    }

    @NonNull
    @Override
    public ProjectManageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_project_emp, parent, false);
        return new ProjectManageViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProjectManageViewHolder holder, int position) {
        ProjectManage ProjectManage = listProjectManage.get(position);
        holder.txtID.setText(ProjectManage.getID());
        holder.txtName.setText("Tên dự án: " + ProjectManage.getName());
        holder.txtUnderTake.setText("Đảm nhận: " + ProjectManage.getUnderTake());
        holder.imgProjectManage.setImageResource(R.drawable.plan_ic);

        holder.txtID.setOnClickListener(v-> {
            Intent intent = new Intent(v.getContext(), Info_Project.class);
            intent.putExtra("name", ProjectManage.getName());
            intent.putExtra("id", ProjectManage.getID());
            intent.putExtra("undertake", ProjectManage.getUnderTake());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listProjectManage.size();
    }

    public static class ProjectManageViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtID, txtUnderTake;
        ImageView imgProjectManage;

        public ProjectManageViewHolder(@NonNull View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.id_project);
            txtName = itemView.findViewById(R.id.name_project);
            txtUnderTake = itemView.findViewById(R.id.undertakeTextView);
            imgProjectManage = itemView.findViewById(R.id.icon_project);
        }
    }
}
