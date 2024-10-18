package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ex.g1.iem.R;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.PaymentHistoryViewHolder> {

    private final List<String> listPaymentHistory;

    // Constructor
    public PaymentHistoryAdapter(List<String> listPaymentHistory) {
        this.listPaymentHistory = listPaymentHistory;
    }

    // ViewHolder class
    public static class PaymentHistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView PaymentHistoryTitle;

        public PaymentHistoryViewHolder(View itemView) {
            super(itemView);
            PaymentHistoryTitle = itemView.findViewById(R.id.payment_history_Title);
        }
    }

    @NonNull
    @Override
    public PaymentHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_payment_history, parent, false);
        return new PaymentHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentHistoryViewHolder holder, int position) {
        String PaymentHistory = listPaymentHistory.get(position);
        holder.PaymentHistoryTitle.setText(PaymentHistory);
    }

    @Override
    public int getItemCount() {
        return listPaymentHistory.size();
    }
}
