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

import java.util.ArrayList;
import java.util.List;
import Class.Department;
import Adapter.DepartmentAdapter;
import ex.g1.iem.R;

public class Depart_ImageButton extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_depart_image_button);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_list_department);
        List<Department> departmentList = new ArrayList<>();
        // tạo 6 phòng ban
        departmentList.add(new Department("Phát triển phần mềm", "Nguyễn Văn A", 10, 10, R.drawable.software_dev_ic));
        departmentList.add(new Department("Hạ tầng IT", "Nguyễn Văn B", 10, 10, R.drawable.it_infrastructure_ic));
        departmentList.add(new Department("An ninh mạng", "Nguyễn Văn C", 10, 10, R.drawable.network_security_ic));
        departmentList.add(new Department("Quản lí dự án", "Nguyễn Văn D", 10, 10, R.drawable.plan_management_ic));
        departmentList.add(new Department("Nhân sự", "Nguyễn Văn E", 10, 10, R.drawable.personnel_ic));
        departmentList.add(new Department("Hỗ trợ kĩ thuật", "Nguyễn Văn F", 10, 10, R.drawable.technical_support_ic));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DepartmentAdapter departmentAdapter = new DepartmentAdapter(departmentList);
        recyclerView.setAdapter(departmentAdapter);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.depart_imagebutton), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}