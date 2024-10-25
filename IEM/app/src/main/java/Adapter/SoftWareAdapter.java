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
import Class.Resource;
import ex.g1.iem.Deep_Event.Resource_Info;
import ex.g1.iem.R;

public class SoftWareAdapter extends RecyclerView.Adapter<SoftWareAdapter.SoftWareViewHolder> {

    private final List<Resource> listSoftWare;

    public SoftWareAdapter(List<Resource> listSoftWare) {
        this.listSoftWare = listSoftWare;
    }

    @NonNull
    @Override
    public SoftWareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_software, parent, false);
        return new SoftWareViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SoftWareViewHolder holder, int position) {
        Resource SoftWare = listSoftWare.get(position);
        holder.txtName.setText(SoftWare.getName());
        holder.txtNum.setText(Integer.toString(SoftWare.getNum()));
        holder.imgSoftWare.setImageResource(R.drawable.software_ic);

        holder.itemView.setOnClickListener(v-> {
            Intent intent = new Intent(v.getContext(), Resource_Info.class);
            intent.putExtra("name", SoftWare.getName());
            intent.putExtra("num", SoftWare.getNum());
            intent.putExtra("type", SoftWare.getType());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listSoftWare.size();
    }

    public static class SoftWareViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtNum;
        ImageView imgSoftWare;

        public SoftWareViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.name_resource);
            txtNum = itemView.findViewById(R.id.num_software);
            imgSoftWare = itemView.findViewById(R.id.icon_software);
        }
    }
}

