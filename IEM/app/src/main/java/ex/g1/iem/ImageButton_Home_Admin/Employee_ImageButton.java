package ex.g1.iem.ImageButton_Home_Admin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import Adapter.EmployeeAdapter;
import java.util.ArrayList;
import java.util.List;

import Class.Employee;

import ex.g1.iem.R;

public class Employee_ImageButton extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_image_button);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_employeeList);
        List<Employee> employeeList = new ArrayList<>();
        // tạo 6 phòng ban
        employeeList.add(new Employee("Tô Công Quân", "Nhân sự", "Trưởng phòng", "1190"));
        employeeList.add(new Employee("Nguyễn Thành Thạo", "IT", "Nhân viên", "1371"));
        employeeList.add(new Employee("Lâm Hoàng Phước", "IT", "Nhân viên", "1153"));
        employeeList.add(new Employee("Huỳnh Ngọc Anh Kiệt", "IT", "Nhân viên", "0718"));
        employeeList.add(new Employee("Lê Hoàng Nam", "IT", "Nhân viên", "1111"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EmployeeAdapter employeeAdapter = new EmployeeAdapter(employeeList);
        recyclerView.setAdapter(employeeAdapter);

        // Xử lí sự kiện khi nhấn nút back
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.employee_imagebutton), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}