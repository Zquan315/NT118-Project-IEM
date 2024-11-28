package ex.g1.iem.Deep_Event;

import android.annotation.SuppressLint;
import android.icu.number.FormattedNumber;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import ex.g1.iem.R;

public class Resource_Info extends AppCompatActivity {
    FirebaseFirestore firestore;
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resource_info);
        FirebaseApp.initializeApp(this);
        firestore = FirebaseFirestore.getInstance();
        // todo: Lấy dữ liệu từ intent
        String name = getIntent().getStringExtra("name");
        String id = getIntent().getStringExtra("id");
        String num = getIntent().getStringExtra("num");
        boolean type = getIntent().getBooleanExtra("type", true);
        TextView nameResourceTextView = findViewById(R.id.nameResource_TextView);
        TextView typeResourceTextView = findViewById(R.id.typeResource_TextView);
        TextView idResourceTextView = findViewById(R.id.idResource_TextView);
        EditText amountResourceEditText = findViewById(R.id.amount_Resource_EditText);
        nameResourceTextView.setText(name);
        idResourceTextView.setText(id);
        typeResourceTextView.setText(type ? "Phần cứng" : "Phần mềm");
        amountResourceEditText.setText(num);

        //todo: Lưu
        findViewById(R.id.save_Button).setOnClickListener(v -> {
            try {
                int amount = Integer.parseInt(amountResourceEditText.getText().toString());
                if (amount <= 0) {
                    Toast.makeText(this, "Vui lòng nhập số lượng là số nguyên dương!", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Vui lòng nhập số lượng là số nguyên dương!", Toast.LENGTH_SHORT).show();
            }
            if (id != null) {
                firestore.collection("Res").document(type ? "HW" : "SW")
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                List<String> array = (List<String>) documentSnapshot.get(id);
                                if (array != null && array.size() > 2) {
                                    array.set(2, amountResourceEditText.getText().toString());

                                    // Cập nhật lại mảng trong Firestore
                                    firestore.collection("Res").document(type ? "HW" : "SW")
                                            .update(id, array)
                                            .addOnSuccessListener(aVoid -> {
                                                amountResourceEditText.setText("");
                                                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(this, "Lỗi khi cập nhật tài nguyên", Toast.LENGTH_SHORT).show();
                                            });
                                }
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Lỗi khi tải tài liệu", Toast.LENGTH_SHORT).show();
                        });

            }
        });

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.resource_info), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}