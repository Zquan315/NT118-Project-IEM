package ex.g1.iem.Deep_Event;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ex.g1.iem.R;

public class Resource_Info extends AppCompatActivity {

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resource_info);

        // Lấy dữ liệu từ intent
        String name = getIntent().getStringExtra("name");
        int num = getIntent().getIntExtra("num", 0);
        boolean type = getIntent().getBooleanExtra("type", true);
        TextView nameResourceTextView = findViewById(R.id.nameResource_TextView);
        TextView typeResourceTextView = findViewById(R.id.typeResource_TextView);
        EditText amountResourceEditText = findViewById(R.id.amount_resource_EditText);
        nameResourceTextView.setText(name);
        typeResourceTextView.setText(type ? "Phần cứng" : "Phần mềm");
        amountResourceEditText.setText(Integer.toString(num));

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.resource_info), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}