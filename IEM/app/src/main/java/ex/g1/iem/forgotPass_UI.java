package ex.g1.iem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class forgotPass_UI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_pass_ui);

        //Xử lý sự kiện khi nút "Xác nhận" được nhấn
        Button confirmBtn = findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(v -> {
            startActivity(new Intent(forgotPass_UI.this, changePass_fromForgot_UI.class));
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.forgotPass), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}