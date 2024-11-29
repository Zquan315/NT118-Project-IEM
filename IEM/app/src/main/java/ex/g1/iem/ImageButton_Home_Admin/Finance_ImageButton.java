package ex.g1.iem.ImageButton_Home_Admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Adapter.PaymentHistoryAdapter;
import ex.g1.iem.R;

public class Finance_ImageButton extends AppCompatActivity {
    EditText sumOfMoneyEditText, contentPaymentEditText, moneyEditText;
    ImageButton editImageButton, doneImageButton;
    Button paymentButton, clearHistoryButton;
    List<String> paymentHistoryList;
    RecyclerView recyclerView;
    PaymentHistoryAdapter paymentHistoryAdapter;
    FirebaseFirestore firestore;
    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_finance_image_button);
        FirebaseApp.initializeApp(this);
        firestore = FirebaseFirestore.getInstance();
        //todo: khoi tao
        sumOfMoneyEditText = findViewById(R.id.sum_of_money_EditText);
        contentPaymentEditText = findViewById(R.id.content_payment_EditText);
        moneyEditText = findViewById(R.id.money_EditText);
        editImageButton = findViewById(R.id.edit_ImageButton);
        doneImageButton = findViewById(R.id.done_ImageButton);
        paymentButton = findViewById(R.id.payment_Button);
        clearHistoryButton = findViewById(R.id.clear_history_Button);

        paymentHistoryList = new ArrayList<String>();
        recyclerView = findViewById(R.id.recyclerView_payment_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentHistoryAdapter = new PaymentHistoryAdapter(paymentHistoryList);
        recyclerView.setAdapter(paymentHistoryAdapter);

        //todo: load tong tien
        loadSumMonney();
        //todo: chinh sua tong tien
        editImageButton.setOnClickListener(v -> sumOfMoneyEditText.setEnabled(true));
        doneImageButton.setOnClickListener(v -> {
            if(sumOfMoneyEditText.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Vui lòng nhập tổng tiền", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                int sumInt = Integer.parseInt(sumOfMoneyEditText.getText().toString());
                if(sumInt <= 0)
                {
                    Toast.makeText(this, "Tổng tiền là số nguyên dương!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //todo: cap nhat tong tien thanh cong
                firestore.collection("Finance").document("Sum")
                        .update("Sum", sumOfMoneyEditText.getText().toString());
                //todo: Them vao lich su thanh toan
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedNow = now.format(formatter);
                String noti = "Tài chính được thiết lập "
                        + sumOfMoneyEditText.getText().toString() + " đồng vào ngày " + formattedNow;
                firestore.collection("History").document("HP")
                        .update("hp", FieldValue.arrayUnion(noti))
                        .addOnSuccessListener(aVoid -> {
                                Toast.makeText(this, "Thành công!", Toast.LENGTH_SHORT).show();
                                loadPaymentHistory();
                                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
                        });
                sumOfMoneyEditText.setEnabled(false);
            }
            catch (NumberFormatException e) {
                Toast.makeText(this, "Tổng tiền là số nguyên dương!", Toast.LENGTH_SHORT).show();
            }

        });

        //todo: thanh toan
        paymentButton.setOnClickListener(v -> {
            if(contentPaymentEditText.getText().toString().isEmpty()
                    || moneyEditText.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            if(sumOfMoneyEditText.isEnabled())
            {
                Toast.makeText(this, "Vui lòng hoàn thành việc chỉnh sửa!", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                int moneyInt = Integer.parseInt(moneyEditText.getText().toString());
                int sumInt = Integer.parseInt(sumOfMoneyEditText.getText().toString());
                if(moneyInt <= 0)
                {
                    Toast.makeText(this, "Số tiền là số nguyên dương!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //todo: thanh toan
                if(moneyInt > sumInt){
                    Toast.makeText(this, "Số tiền không đủ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int newSum = sumInt - moneyInt;
                String newSumStr = Integer.toString(newSum);

                //todo: cap nhat tien moi
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedNow = now.format(formatter);
                firestore.collection("Finance").document("Sum")
                        .update("Sum", newSumStr);
                loadSumMonney();
                String newNoti = "Bạn đã thanh toán " + moneyInt + " đồng vào ngày "
                        + formattedNow + ".Nội dung: " +
                        contentPaymentEditText.getText().toString();
                firestore.collection("History").document("HP")
                        .update("hp", FieldValue.arrayUnion(newNoti))
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, " Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                            loadPaymentHistory();
                            contentPaymentEditText.setText("");
                            moneyEditText.setText("");
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
                        });

            }
            catch (NumberFormatException e)
            {
                Toast.makeText(this, "Số tiền là số nguyên dương!", Toast.LENGTH_SHORT).show();
            }
        });

        //todo: load lich su thanh toan
        loadPaymentHistory();

        //todo: Xoa lich su thanh toan
        clearHistoryButton.setOnClickListener(v -> {
            firestore.collection("History").document("HP")
                    .update("hp", FieldValue.delete())
                    .addOnSuccessListener(aVoid -> {
                        paymentHistoryList.clear();
                        paymentHistoryAdapter.notifyDataSetChanged();
                        Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                        loadPaymentHistory();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
                    });

        });
        //todo: back button
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.finance_imagebutton), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    void loadPaymentHistory()
    {
        paymentHistoryList.clear();
        firestore.collection("History").document("HP").get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<Object> hpArray = (List<Object>) documentSnapshot.get("hp");
                        if (hpArray != null) {
                            for (Object element : hpArray) {
                                paymentHistoryList.add(element.toString());
                                paymentHistoryAdapter.notifyDataSetChanged();
                            }
                        }
                        else {
                            Toast.makeText(this, "Không có lịch sử thanh toán", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(this, "Không có lịch sử thanh toán", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
                });
    }

    void loadSumMonney()
    {
        firestore.collection("Finance").document("Sum").get().
                addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String sum = task.getResult().getString("Sum");
                        sumOfMoneyEditText.setText(sum);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
                });
    }
}