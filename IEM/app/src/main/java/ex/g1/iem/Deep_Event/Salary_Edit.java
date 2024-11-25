package ex.g1.iem.Deep_Event;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import ex.g1.iem.R;

public class Salary_Edit extends AppCompatActivity {
    // TODO: Update with your activity layout
    DatabaseReference DBRealtime;
    FirebaseFirestore firestore;
    boolean isEdit = false;
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_salary_edit);
        FirebaseApp.initializeApp(this);
        DBRealtime = FirebaseDatabase.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();

        // Lấy tên nhân viên từ intent
        String name = getIntent().getStringExtra("employee_name");
        TextView nameTextView = findViewById(R.id.name_TextView);
        nameTextView.setText(name);

        //Lấy mã nhân viên
        String id = getIntent().getStringExtra("employee_id");
        TextView idTextView = findViewById(R.id.id_TextView);
        idTextView.setText(id);

        //Lấy lương cơ bản
        String basicSalary = getIntent().getStringExtra("employee_basicSalary");
        EditText basicSalaryEditText = findViewById(R.id.basicSalary_EditText);
        basicSalaryEditText.setText(basicSalary);

        // lấy số ngày nghỉ
        String leaveDays = getIntent().getStringExtra("employee_leaveDays");
        EditText leaveDaysEditText = findViewById(R.id.leaveDay_EditText);
        leaveDaysEditText.setText(leaveDays);
        EditText perksEditText = findViewById(R.id.perk_EditText);

        assert id != null;
        try {
            firestore.collection("Salary").document(id).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String totalSalary = task.getResult().getString("totalSalary");
                    EditText totalSalaryEditText = findViewById(R.id.sum_of_salary_EditText);
                    totalSalaryEditText.setText(totalSalary);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // tính lương tổng kết
        EditText totalSalaryEditText = findViewById(R.id.sum_of_salary_EditText);
        findViewById(R.id.calc_Button).setOnClickListener(v -> {
            String basicSalaryStr = basicSalaryEditText.getText().toString();
            String leaveDaysStr = leaveDaysEditText.getText().toString();
            String perksStr = perksEditText.getText().toString();
            if(basicSalaryEditText.getText().toString().isEmpty()
                    || leaveDaysEditText.getText().toString().isEmpty()
                    || basicSalaryEditText.getText().toString().equals("0")
                    || perksEditText.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }


            try {
                int basicSalaryInt = Integer.parseInt(basicSalaryStr);
                int leaveDaysInt = Integer.parseInt(leaveDaysStr);
                int perk = Integer.parseInt(perksStr);

                if (basicSalaryInt <= 0 || leaveDaysInt < 0 || perk < 0) {
                    Toast.makeText(this, "Các giá trị phải là số nguyên dương", Toast.LENGTH_SHORT).show();
                    return;
                }

                isEdit = true;
                int totalSalaryInt = basicSalaryInt - leaveDaysInt * 250000;
                int sum = totalSalaryInt + (perk * totalSalaryInt / 100);
                totalSalaryEditText.setText(Integer.toString(sum));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Vui lòng nhập các giá trị hợp lệ", Toast.LENGTH_SHORT).show();
            }
                });

        //todo: luu
        findViewById(R.id.save_Button).setOnClickListener(v -> {
            if(!isEdit)
            {
                Toast.makeText(this, "Vui lòng tính lương", Toast.LENGTH_SHORT).show();
                return;
            }
            firestore.collection("Salary").document(id).update("totalSalary", totalSalaryEditText.getText().toString());
            firestore.collection("Salary").document(id).update("basicSalary", basicSalaryEditText.getText().toString());
            firestore.collection("Salary").document(id).update("leaveDays", Integer.parseInt(leaveDaysEditText.getText().toString()));
            Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();

        });

        //todo: reset
        findViewById(R.id.reset_Button).setOnClickListener(v -> {
            leaveDaysEditText.setText("0");
            perksEditText.setText("0");
            totalSalaryEditText.setText("0");
            firestore.collection("Salary").document(id).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String salary = task.getResult().getString("basicSalary");
                    basicSalaryEditText.setText(salary);
                }
            });
            isEdit = false;
            firestore.collection("Salary").document(id).update("totalSalary", "0");
            firestore.collection("Salary").document(id).update("leaveDays", 0);
            Toast.makeText(this, "Reset thành công", Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.salary_edit), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}