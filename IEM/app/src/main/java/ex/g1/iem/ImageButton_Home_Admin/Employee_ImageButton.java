package ex.g1.iem.ImageButton_Home_Admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import Adapter.EmployeeAdapter;
import java.util.ArrayList;
import java.util.List;

import Class.Employee;

import ex.g1.iem.R;

public class Employee_ImageButton extends AppCompatActivity {

    FirebaseFirestore firestore;
    String usernameAdmin;
    TextView num_of_employee_emp;
    DatabaseReference DBRealtime;
    ArrayList<Employee> employeeList;
    RecyclerView recyclerView;
    EmployeeAdapter employeeAdapter;
    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        usernameAdmin = getIntent().getStringExtra("username");
        setContentView(R.layout.activity_employee_image_button);
        num_of_employee_emp = findViewById(R.id.num_of_employee_emp);
        recyclerView = findViewById(R.id.recyclerView_employeeList);
        employeeList = new ArrayList<>();
        FirebaseApp.initializeApp(this);
        firestore = FirebaseFirestore.getInstance();
        DBRealtime = FirebaseDatabase.getInstance().getReference();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        employeeAdapter = new EmployeeAdapter(employeeList, usernameAdmin);
        recyclerView.setAdapter(employeeAdapter);
        // todo: load danh sách nhân viên
        firestore.collection("Employee")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        String id = document.getId(); // Lấy ID của document
                        if(id.equals("tocongquanmmtt"))
                            continue;
                        String name = document.getString("name");
                        String department = document.getString("depart");
                        String email = document.getString("email");
                        String gender = document.getString("gender");
                        String key = document.getString("key");
                        String phone = document.getString("phone");
                        String role = document.getString("role");
                        employeeList.add(new Employee(id, name, key, phone, email,
                                department, gender, role));
                    }
                    // todo: Cập nhật giao diện sau khi lấy dữ liệu thành công
                    employeeAdapter.notifyDataSetChanged();
                    //todo: hiển thị số lượng nhân viên
                    num_of_employee_emp.setText(String.valueOf(employeeList.size()));
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                });


        // Xử lí sự kiện khi nhấn nút back
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.employee_imagebutton), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}