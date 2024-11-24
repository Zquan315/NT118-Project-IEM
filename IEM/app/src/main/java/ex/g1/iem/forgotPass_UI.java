package ex.g1.iem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class forgotPass_UI extends AppCompatActivity {
    String username;
    EditText key;
    FirebaseFirestore firestore;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_pass_ui);
        FirebaseApp.initializeApp(this);
        firestore = FirebaseFirestore.getInstance();
        username = getIntent().getStringExtra("username");
        key = findViewById(R.id.keyEditText);
        //Xử lý sự kiện khi nút "Xác nhận" được nhấn
        Button confirmBtn = findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(v -> {
           if(key.getText().toString().isEmpty())
           {
               key.setError("Trống");
               return;
           }
           try {
                firestore.collection("Employee").document(username).get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                String keyEmp = documentSnapshot.getString("key");
                                if (keyEmp != null && keyEmp.equals(key.getText().toString()))
                                {
                                    Intent intent = new Intent(forgotPass_UI.this, changePass_fromForgot_UI.class);
                                    intent.putExtra("username", username);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    key.setError("Sai mã");
                                }
                            }
                            else
                                Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
                        });
           } catch (Exception e) {
               Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
           }
        });

        findViewById(R.id.backBtn).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.forgotPass), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}