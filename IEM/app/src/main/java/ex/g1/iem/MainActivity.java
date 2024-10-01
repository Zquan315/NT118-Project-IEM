package ex.g1.iem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

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
            if(user.getText().toString().equals("admin") && pass.getText().toString().equals("admin")){
                Intent intent = new Intent(MainActivity.this, mainScreen_admin_UI.class);
                startActivity(intent);
            }
            else if(user.getText().toString().equals("emp") && pass.getText().toString().equals("emp")) {
                Intent intent = new Intent(MainActivity.this, mainScreen_emp_UI.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}