package ex.g1.iem.ImageButton_Home_Admin;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import Class.Department;
import Adapter.DepartmentAdapter;
import ex.g1.iem.R;

public class Depart_ImageButton extends AppCompatActivity {
    FirebaseFirestore firestore;
    DatabaseReference DBRealtime;
    String name, id_depart, id_header;
    int image,  amount_emp, amount_pj;
    private DepartmentAdapter departmentAdapter;
    private ArrayList<Department> departmentList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_depart_image_button);

        FirebaseApp.initializeApp(this);
        firestore = FirebaseFirestore.getInstance();
        DBRealtime = FirebaseDatabase.getInstance().getReference();

        RecyclerView recyclerView = findViewById(R.id.recyclerView_list_department);
        departmentList = new ArrayList<>();

        departmentAdapter = new DepartmentAdapter(departmentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(departmentAdapter);

        // Load dữ liệu
        loadDepartments();

        // Xử lí sự kiện khi nhấn nút back
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.depart_imagebutton), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadDepartments() {
        firestore.collection("Department")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            departmentList.clear();

                            for (QueryDocumentSnapshot document : querySnapshot) {
                                name = document.getId();
                                Map<String, Object> fields = document.getData();

                                id_header = Objects.requireNonNull(fields.getOrDefault("id_header", "")).toString();
                                id_depart = Objects.requireNonNull(fields.getOrDefault("id_depart", "")).toString();

                                amount_emp = parseIntSafely(Objects.requireNonNull(fields.getOrDefault("amount_emp", "0")).toString());
                                amount_pj = parseIntSafely(Objects.requireNonNull(fields.getOrDefault("amount_pj", "0")).toString());

                                // Set image based on department name
                                switch(name) {
                                    case "Phát triển phần mềm":
                                        image = R.drawable.software_dev_ic;
                                        break;
                                    case "An ninh mạng":
                                        image = R.drawable.network_security_ic;
                                        break;
                                    case "Hạ tầng IT":
                                        image = R.drawable.it_infrastructure_ic;
                                        break;
                                    case "Hỗ trợ kỹ thuật":
                                        image = R.drawable.technical_support_ic;
                                        break;
                                    case "Nhân sự":
                                        image = R.drawable.personnel_ic;
                                        break;
                                    default:
                                        image = R.drawable.plan_management_ic;
                                        break;
                                }

                                Department department = new Department(
                                        name,
                                        id_depart,
                                        id_header,
                                        amount_emp,
                                        amount_pj,
                                        image
                                );
                                departmentList.add(department);
                            }
                            departmentAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(Depart_ImageButton.this,
                                    "Không có phòng ban nào", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Depart_ImageButton.this,
                                "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private int parseIntSafely(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}