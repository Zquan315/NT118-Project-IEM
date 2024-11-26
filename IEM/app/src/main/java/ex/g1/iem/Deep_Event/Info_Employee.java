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

import java.util.Objects;

import ex.g1.iem.R;

public class Info_Employee extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info_employee);

        //TODO: Update with your layout
        TextView name = findViewById(R.id.name_TextView);
        TextView id = findViewById(R.id.id_TextView);
        EditText depart = findViewById(R.id.depart_info);
        EditText phone = findViewById(R.id.phone_info);
        EditText email = findViewById(R.id.email_info);
        EditText gender = findViewById(R.id.sex_info);
        EditText role = findViewById(R.id.role_info);
        String ph = getIntent().getStringExtra("phone");
        String e = getIntent().getStringExtra("email");

        name.setText(getIntent().getStringExtra("name"));
        id.setText(getIntent().getStringExtra("id"));
        depart.setText(getIntent().getStringExtra("depart"));
        phone.setText(Objects.equals(ph, "") ? "Trống" : ph);
        email.setText(Objects.equals(e, "") ? "Trống" : e);
        gender.setText(getIntent().getStringExtra("gender"));
        role.setText(getIntent().getStringExtra("role"));

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.info_employee), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}