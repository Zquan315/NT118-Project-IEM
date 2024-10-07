package Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ex.g1.iem.R;

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.AlertViewHolder> {

    private final List<String> listAlert;

    // Constructor
    public AlertAdapter(List<String> listAlert) {
        this.listAlert = listAlert;
    }

    // ViewHolder class
    public static class AlertViewHolder extends RecyclerView.ViewHolder {
        public TextView alertTitle;

        public AlertViewHolder(View itemView) {
            super(itemView);
            alertTitle = itemView.findViewById(R.id.alertTitle);
        }
    }

    @NonNull
    @Override
    public AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_alert_demo, parent, false);
        return new AlertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertViewHolder holder, int position) {
        String alert = listAlert.get(position);
        holder.alertTitle.setText(alert);
    }

    @Override
    public int getItemCount() {
        return listAlert.size();
    }
}

