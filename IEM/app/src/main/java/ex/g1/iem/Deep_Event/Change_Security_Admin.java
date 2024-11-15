package ex.g1.iem.Deep_Event;

import android.annotation.SuppressLint;
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

import ex.g1.iem.R;

public class Change_Security_Admin extends AppCompatActivity {
    String usernameAdmin;
    EditText currentPassEditText, newpassEditText, confirmPassEditText;
    Button changeBtn;
    DatabaseReference DBRealtime;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_security_admin);
        FirebaseApp.initializeApp(this);
        DBRealtime = FirebaseDatabase.getInstance().getReference();
        currentPassEditText = findViewById(R.id.currentPassEditText);
        newpassEditText = findViewById(R.id.passEditText);
        confirmPassEditText = findViewById(R.id.confirmPassEditText);
        changeBtn = findViewById(R.id.changeBtn);
        usernameAdmin = getIntent().getStringExtra("username");
        // todo: xử lý sự kiện khi nhấn nút thay đổi
        changeBtn.setOnClickListener(v -> {
            try {
                if(currentPassEditText.getText().toString().isEmpty()
                || newpassEditText.getText().toString().isEmpty()
                || confirmPassEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!newpassEditText.getText().toString().equals(confirmPassEditText.getText().toString()))
                {
                    confirmPassEditText.setError("Mật khẩu không khớp");
                    return;
                }
                DBRealtime.child("Account").child(usernameAdmin).child("password").get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                String currentPasswordFromDB = task.getResult().getValue(String.class);
                                if (currentPasswordFromDB != null && currentPasswordFromDB.equals(currentPassEditText.getText().toString())) {
                                    // Mật khẩu hiện tại đúng, cập nhật mật khẩu mới
                                    DBRealtime.child("Account").child(usernameAdmin).child("password")
                                            .setValue(newpassEditText.getText().toString())
                                            .addOnCompleteListener(updateTask -> {
                                                if (updateTask.isSuccessful()) {
                                                    Toast.makeText(this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(this, "Lỗi khi cập nhật mật khẩu", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    currentPassEditText.setError("Mật khẩu hiện tại không đúng");
                                    return;
                                }
                            } else {
                                Toast.makeText(this, "Lỗi khi kiểm tra mật khẩu", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Lỗi thay đổi mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });

        //Xử lý sự kiện khi nhấn nút back
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.change_security_admin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}