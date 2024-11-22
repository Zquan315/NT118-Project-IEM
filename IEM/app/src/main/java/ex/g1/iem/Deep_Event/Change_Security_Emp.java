package ex.g1.iem.Deep_Event;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ex.g1.iem.MainActivity;
import ex.g1.iem.R;

public class Change_Security_Emp extends AppCompatActivity {
    String usernameEmp;
    DatabaseReference DBRealtime;
    FirebaseFirestore firestore;
    EditText currentPassEditText, newpassEditText, confirmPassEditText, keyEditText;
    Button changeBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_security_emp);
        usernameEmp = getIntent().getStringExtra("username");
        FirebaseApp.initializeApp(this);
        DBRealtime = FirebaseDatabase.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();
        currentPassEditText = findViewById(R.id.currentPassEditText);
        newpassEditText = findViewById(R.id.passEditText);
        confirmPassEditText = findViewById(R.id.confirmPassEditText);
        keyEditText = findViewById(R.id.keyEditText);
        changeBtn = findViewById(R.id.changeBtn);
        // TODO: xử lý đổi mật khẩu
        changeBtn.setOnClickListener(v -> {
            try {
                if (currentPassEditText.getText().toString().isEmpty()
                        || newpassEditText.getText().toString().isEmpty()
                        || confirmPassEditText.getText().toString().isEmpty()
                        || keyEditText.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newpassEditText.getText().toString().equals(confirmPassEditText.getText().toString())) {
                    confirmPassEditText.setError("Mật khẩu không khớp");
                    return;
                }

                // Lấy key từ Firestore
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection("Employee").document(usernameEmp).get()
                        .addOnCompleteListener(firestoreTask -> {
                            if (firestoreTask.isSuccessful()) {
                                DocumentSnapshot document = firestoreTask.getResult();
                                if (document != null && document.exists()) {
                                    String keyFromDB = document.getString("key");
                                    if (keyFromDB != null && keyFromDB.equals(keyEditText.getText().toString())) {
                                        // Key hợp lệ, tiếp tục kiểm tra mật khẩu
                                        DBRealtime.child("Account").child(usernameEmp).child("password").get()
                                                .addOnCompleteListener(task -> {
                                                    if (task.isSuccessful()) {
                                                        String currentPasswordFromDB = task.getResult().getValue(String.class);
                                                        if (currentPasswordFromDB != null && currentPasswordFromDB.equals(currentPassEditText.getText().toString())) {
                                                            // Mật khẩu hiện tại đúng, cập nhật mật khẩu mới
                                                            DBRealtime.child("Account").child(usernameEmp).child("password")
                                                                    .setValue(newpassEditText.getText().toString())
                                                                    .addOnCompleteListener(updateTask -> {
                                                                        if (updateTask.isSuccessful()) {
                                                                            Toast.makeText(this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();

                                                                            //todo: thông báo bắt buộc đăng xuất
                                                                            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                                                                            dialog.setTitle("Thông báo");
                                                                            dialog.setMessage("Bạn bắt buộc phải đăng xuất!");

                                                                            // Nút OK
                                                                            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(DialogInterface dialog, int which) {
                                                                                    startActivity(new Intent(Change_Security_Emp.this, MainActivity.class));
                                                                                    finish();
                                                                                }
                                                                            });
                                                                            AlertDialog alertDialog = dialog.create();
                                                                            alertDialog.show();
                                                                        } else {
                                                                            Toast.makeText(this, "Lỗi khi cập nhật mật khẩu", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                        }
                                                        else {
                                                            currentPassEditText.setError("Mật khẩu hiện tại không đúng");
                                                        }
                                                    }
                                                    else {
                                                        Toast.makeText(this, "Lỗi khi kiểm tra mật khẩu", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                    else {
                                        keyEditText.setError("Key không chính xác");
                                    }
                                }

                                else {
                                    Toast.makeText(this, "Không tìm thấy tài khoản trong Firestore", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(this, "Lỗi khi kiểm tra key từ Firestore", Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (Exception e) {
                Toast.makeText(this, "Lỗi thay đổi mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });


        //xử lý nút quay lại
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.change_security_emp), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}