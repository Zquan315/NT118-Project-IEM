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
import Class.Resource;
import ex.g1.iem.R;

public class HardWareAdapter extends RecyclerView.Adapter<HardWareAdapter.HardWareViewHolder> {

    private final List<Resource> listHardWare;

    public HardWareAdapter(List<Resource> listHardWare) {
        this.listHardWare = listHardWare;
    }

    @NonNull
    @Override
    public HardWareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_hardware, parent, false);
        return new HardWareViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HardWareViewHolder holder, int position) {
        Resource HardWare = listHardWare.get(position);
        holder.txtName.setText(HardWare.getName());
        holder.txtNum.setText(Integer.toString(HardWare.getNum()));
        holder.imgHardWare.setImageResource(R.drawable.hardware_ic);
    }

    @Override
    public int getItemCount() {
        return listHardWare.size();
    }

    public static class HardWareViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtNum;
        ImageView imgHardWare;

        public HardWareViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.name_resource);
            txtNum = itemView.findViewById(R.id.num_hardware);
            imgHardWare = itemView.findViewById(R.id.icon_hardware);
        }
    }
}

