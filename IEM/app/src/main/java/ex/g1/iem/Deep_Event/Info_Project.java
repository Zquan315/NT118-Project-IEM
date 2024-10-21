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

public class Info_Project extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info_project);

        TextView nameProjectTextView = findViewById(R.id.nameProject_TextView);
        TextView idProjectTextView = findViewById(R.id.idProject_TextView);
        EditText undertakeTextView = findViewById(R.id.undertake_info);
        TextView descriptionTextView = findViewById(R.id.project_description);

        nameProjectTextView.setText(getIntent().getStringExtra("name"));
        idProjectTextView.setText(getIntent().getStringExtra("id"));
        undertakeTextView.setText(getIntent().getStringExtra("undertake"));

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.info_project), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}