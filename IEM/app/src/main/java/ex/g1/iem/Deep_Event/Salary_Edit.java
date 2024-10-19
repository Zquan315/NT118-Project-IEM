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

public class Salary_Edit extends AppCompatActivity {
    // TODO: Update with your activity layout

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_salary_edit);

        // Lấy tên nhân viên từ intent
        String name = getIntent().getStringExtra("employee_name");
        TextView nameTextView = findViewById(R.id.name_TextView);
        nameTextView.setText(name);

        //Lấy lương cơ bản
        String basicSalary = getIntent().getStringExtra("employee_basicSalary");
        EditText basicSalaryEditText = findViewById(R.id.basicSalary_EditText);
        basicSalaryEditText.setText(basicSalary);

        // lấy số ngày nghỉ
        String leaveDays = getIntent().getStringExtra("employee_leaveDays");
        EditText leaveDaysEditText = findViewById(R.id.leaveDay_EditText);
        leaveDaysEditText.setText(leaveDays);



        // tính lương tổng kết
        EditText totalSalaryEditText = findViewById(R.id.sum_of_salary_EditText);
        findViewById(R.id.calc_Button).setOnClickListener(v -> {
                    EditText perksEditText = findViewById(R.id.perk_EditText);
                    int perk = Integer.parseInt(perksEditText.getText().toString());
                    int basicSalaryInt = Integer.parseInt(basicSalary);
                    int leaveDaysInt = Integer.parseInt(leaveDays);
                    int totalSalaryInt = basicSalaryInt - leaveDaysInt * 250000;
                    int sum = totalSalaryInt + (perk * totalSalaryInt / 100);
                    totalSalaryEditText.setText(Integer.toString(sum));
                });
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.salary_edit), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}