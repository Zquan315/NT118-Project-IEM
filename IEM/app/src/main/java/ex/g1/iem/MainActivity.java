package ex.g1.iem;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference DBRealtime;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // khoi tao fireabse
        FirebaseApp.initializeApp(this);
        DBRealtime = FirebaseDatabase.getInstance().getReference();

         //Xử lý sự kiện khi nút "Quên mật khẩu" được nhấn
        Button forgotPass = findViewById(R.id.forgotPasswordTextBtn);
        forgotPass.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, forgotPass_UI.class));
        });


        //Xử lý sự kiện khi nút "Đăng nhập" được nhấn
        EditText user = findViewById(R.id.UserEditText);
        EditText pass = findViewById(R.id.passEditText);
        Button login = findViewById(R.id.loginBtn);
        login.setOnClickListener(v -> {
            try {
                if (user.getText().toString().isEmpty()) {
                    user.setError("Trống");
                    return;
                }
                if (pass.getText().toString().isEmpty()) {
                    pass.setError("Trống");
                    return;
                }

                username = user.getText().toString();
                password = pass.getText().toString();

                getPasswordFromFirebaseRealtime(username, new FirebaseCallback() {
                    @Override
                    public void onCallback(String retrievedPassword) {
                        if (retrievedPassword.equals("-1")) {
                            user.setError("Không tìm thấy tài khoản");
                        }
                        else if (retrievedPassword.equals("-2")) {
                            Toast.makeText(MainActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                        }
                        else if (retrievedPassword.equals(password)) {
                            Intent intent;
                            if (username.equals("admin")) {
                                intent = new Intent(MainActivity.this, mainScreen_admin_UI.class);
                            }
                            else {
                                intent = new Intent(MainActivity.this, mainScreen_emp_UI.class);
                            }
                            intent.putExtra("username", username);
                            startActivity(intent);
                            finish(); // kết thúc activity
                        }
                        else {
                            pass.setError("Sai mật khẩu");
                        }
                    }
                });

            } catch (Exception e) {
                Toast.makeText(this, "Lỗi đăng nhập", Toast.LENGTH_SHORT).show();
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getPasswordFromFirebaseRealtime(String id, FirebaseCallback callback) {
        DBRealtime.child("Account").child(id).child("password").get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                callback.onCallback(task.getResult().getValue(String.class));
                            }
                            else {
                                callback.onCallback("-1"); // không tìm thấy id trong Firebase Realtime
                            }
                        }
                        else {
                            callback.onCallback("-2"); // lỗi khi truy cập Firebase Realtime
                        }
                    }
                });
    }
    private interface FirebaseCallback {
        void onCallback(String retrievedPassword);
    }

}