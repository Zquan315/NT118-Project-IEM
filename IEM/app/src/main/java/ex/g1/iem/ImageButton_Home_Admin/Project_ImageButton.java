package ex.g1.iem.ImageButton_Home_Admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import Class.ProjectManage;
import Adapter.ProjectManageAdapter;
import ex.g1.iem.R;

public class Project_ImageButton extends AppCompatActivity {
    String usernameAdmin;
    RecyclerView recyclerView;
    List<ProjectManage> projectManageList;
    ProjectManageAdapter projectManageAdapter;
    FirebaseFirestore firestore;
    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        usernameAdmin = getIntent().getStringExtra("username");
        setContentView(R.layout.activity_project_image_button);
        recyclerView = findViewById(R.id.recyclerView_projectList);
        projectManageList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        projectManageAdapter = new ProjectManageAdapter(projectManageList, usernameAdmin);
        recyclerView.setAdapter(projectManageAdapter);
        FirebaseApp.initializeApp(this);
        firestore = FirebaseFirestore.getInstance();
        //todo: load Project
        firestore.collection("Project")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        String id = document.getId(); // Lấy ID của document
//                        if(id.equals("id"))
//                            continue;
                        String description = document.getString("description");
                        String name = document.getString("name");
                        String underTake = document.getString("underTake");

                        projectManageList.add(new ProjectManage(id, name,underTake, description ));
                    }
                    // Cập nhật giao diện sau khi lấy dữ liệu thành công
                    projectManageAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                });

        // Xử lí sự kiện khi nhấn nút back
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.project_imagebutton), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}