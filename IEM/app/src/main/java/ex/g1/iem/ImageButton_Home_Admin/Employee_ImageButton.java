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
        employeeList.add(new Employee("Tô Công Quân", "1190", "1", "1190", "dddh@gm.com", "Phát triển phần mềm", "Nam", "nhan vien"));
        employeeList.add(new Employee("Nguyễn Thành Thạo", "1371", "2", "1371", "dddh@gm.com", "Hạ tầng IT", "Nam", "nhan vien"));
        employeeList.add(new Employee("Lâm Hoàng Phước", "1153", "3", "1153", "dddh@gm.com", "An ninh mạng", "Nam", "nhan vien"));
        employeeList.add(new Employee("Huỳnh Ngọc Anh Kiệt", "0718", "4", "0718", "dddh@gm.com", "Quản lý dự án", "Nam", "nhan vien"));

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