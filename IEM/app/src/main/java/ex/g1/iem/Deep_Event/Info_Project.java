package ex.g1.iem.Deep_Event;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import ex.g1.iem.R;

public class Info_Project extends AppCompatActivity {
    String username;
    FirebaseFirestore firestore;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info_project);
        FirebaseApp.initializeApp(this);
        firestore = FirebaseFirestore.getInstance();
        username = getIntent().getStringExtra("username");

        TextView nameProjectTextView = findViewById(R.id.nameProject_TextView);
        TextView idProjectTextView = findViewById(R.id.idProject_TextView);
        EditText undertakeTextView = findViewById(R.id.undertake_info);
        TextView descriptionTextView = findViewById(R.id.project_description);
        Button finishButton = findViewById(R.id.finish_Button);

        nameProjectTextView.setText(getIntent().getStringExtra("name"));
        idProjectTextView.setText(getIntent().getStringExtra("id"));
        undertakeTextView.setText(getIntent().getStringExtra("undertake"));
        String id_PJ = getIntent().getStringExtra("id");
        //todo: load description
        if (id_PJ != null) {
            firestore.collection("Project").document(id_PJ).get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful())
                        {
                            String description = task.getResult().getString("description");
                            descriptionTextView.setText(description);
                        }
                    });
        }
        //todo: chi co truong phong moi thay nut Hoan thanh
        if(!username.equals("admin"))
        {
            firestore.collection("Employee").document(username).get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful())
                        {
                            String role = task.getResult().getString("role");
                            if(role != null && role.equals("Trưởng phòng"))
                            {
                                finishButton.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                finishButton.setVisibility(View.GONE);
                            }
                        }
                    });
        }
        else
            finishButton.setVisibility(View.GONE);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.info_project), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}