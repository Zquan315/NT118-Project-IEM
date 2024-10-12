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
import Class.ProjectManage;
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
        holder.txtProgress.setText("Tiến độ: " + ProjectManage.getProgress() + "%");
        holder.imgProjectManage.setImageResource(R.drawable.plan_ic);
    }

    @Override
    public int getItemCount() {
        return listProjectManage.size();
    }

    public static class ProjectManageViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtID, txtProgress;
        ImageView imgProjectManage;

        public ProjectManageViewHolder(@NonNull View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.id_project);
            txtName = itemView.findViewById(R.id.name_project);
            txtProgress = itemView.findViewById(R.id.progressTextView);
            imgProjectManage = itemView.findViewById(R.id.icon_project);
        }
    }
}
