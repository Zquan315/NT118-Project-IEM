package ex.g1.iem.ImageButton_Home_Admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import Class.ProjectManage;
import Adapter.ProjectManageAdapter;
import ex.g1.iem.R;

public class Project_ImageButton extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_project_image_button);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_projectList);
        List<ProjectManage> projectManageList = new ArrayList<>();
        for (int i = 1; i < 9; i++)
        {
            int a = i + 80;
            projectManageList.add(new ProjectManage("00" + Integer.toString(i),
                    "IEM" + Integer.toString(i*5), "Nhân sự"));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProjectManageAdapter projectManageAdapterAdapter = new ProjectManageAdapter(projectManageList);
        recyclerView.setAdapter(projectManageAdapterAdapter);

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