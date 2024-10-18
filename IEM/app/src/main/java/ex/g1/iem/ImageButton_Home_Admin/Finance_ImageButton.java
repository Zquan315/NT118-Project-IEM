package ex.g1.iem.ImageButton_Home_Admin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapter.PaymentHistoryAdapter;
import ex.g1.iem.R;

public class Finance_ImageButton extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_finance_image_button);

        List<String> paymentHistoryList = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            paymentHistoryList.add("Thanh toán " + (i + 1));
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerView_payment_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PaymentHistoryAdapter adapter = new PaymentHistoryAdapter(paymentHistoryList);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.finance_imagebutton), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}