package ex.g1.iem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

public class changePass_fromForgot_UI extends AppCompatActivity {

    String username;
    EditText newPass, confirmPass;
    DatabaseReference DBRealtime;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_pass_from_forgot_ui);
        FirebaseApp.initializeApp(this);
        DBRealtime = FirebaseDatabase.getInstance().getReference();
        username = getIntent().getStringExtra("username");
        newPass = findViewById(R.id.passEditText);
        confirmPass = findViewById(R.id.confirmPassEditText);

        //todo: Xử lý sự kiện khi nút "Xác nhận" được nhấn
        findViewById(R.id.changeBtn).setOnClickListener(v -> {
            if(newPass.getText().toString().isEmpty())
            {
                newPass.setError("Trống");
                return;
            }
            if(!confirmPass.getText().toString().equals(newPass.getText().toString()))
            {
                confirmPass.setError("Mật khâu không khớp");
                return;
            }
            try {
                DBRealtime.child("Account").child(username).child("password").setValue(newPass.getText().toString());
                Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            }
            catch (Exception e) {
                Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.backBtn).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.changePass_fromForgot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}