package Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import Class.Alert;
import java.util.List;

import ex.g1.iem.Deep_Event.Info_Alert;
import ex.g1.iem.R;

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.AlertViewHolder> {

    private final List<Alert> listAlert;

    // Constructor
    public AlertAdapter(List<Alert> listAlert) {
        this.listAlert = listAlert;
    }

    // ViewHolder class
    public static class AlertViewHolder extends RecyclerView.ViewHolder {
        public TextView alertTitle, alertID;

        public AlertViewHolder(View itemView) {
            super(itemView);
            alertTitle = itemView.findViewById(R.id.title_alert);
            alertID = itemView.findViewById(R.id.id_alert);
        }
    }

    @NonNull
    @Override
    public AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_alert_demo, parent, false);
        return new AlertViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AlertViewHolder holder, int position) {
        Alert alert = listAlert.get(position);
        holder.alertTitle.setText(alert.getTitle());
        holder.alertID.setText(alert.getId());
        //todo: xử lí sự kiện khi nhấn vào thông báo
        holder.itemView.setOnClickListener(v -> {
            //todo: chuyển sang màn hình chi tiết thông báo
            Intent intent = new Intent(holder.itemView.getContext(), Info_Alert.class);
            intent.putExtra("alertID", holder.alertID.getText().toString());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listAlert.size();
    }
}

