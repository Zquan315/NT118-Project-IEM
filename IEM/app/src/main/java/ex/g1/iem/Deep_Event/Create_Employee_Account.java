package ex.g1.iem.Deep_Event;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

import ex.g1.iem.R;

public class Create_Employee_Account extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_employee_account);

        // Spinner for Phòng ban
        Spinner departmentSpinner = findViewById(R.id.depart_info_emp);
        ArrayAdapter<CharSequence> departmentAdapter = ArrayAdapter.createFromResource(this,
                R.array.departments_array, R.layout.spinner_item);
        departmentAdapter.setDropDownViewResource( R.layout.spinner_dropdown_item);
        departmentSpinner.setAdapter(departmentAdapter);
        departmentSpinner.setSelection(0);

        // Spinner for Giới tính
        Spinner genderSpinner = findViewById(R.id.sex_info_emp);
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, R.layout.spinner_item);
        genderAdapter.setDropDownViewResource( R.layout.spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setSelection(0);

        //spinner chức vụ
        Spinner roleSpinner = findViewById(R.id.role_info_emp);
        ArrayAdapter<CharSequence> roleAdapter = ArrayAdapter.createFromResource(this,
                R.array.role_array,  R.layout.spinner_item);
        roleAdapter.setDropDownViewResource( R.layout.spinner_dropdown_item);
        roleSpinner.setAdapter(roleAdapter);
        roleSpinner.setSelection(0);

        //random id và key
        findViewById(R.id.random_id_key_button).setOnClickListener(v -> {
            // TODO: Implement random ID and key logic
            if (departmentSpinner.getSelectedItem().toString().equals("None") ||
                    genderSpinner.getSelectedItem().toString().equals("None") ||
                    roleSpinner.getSelectedItem().toString().equals("None")) {
                Toast.makeText(this, "Vui lòng chọn đầy đủ thông tin phòng ban, giới tính, và chức vụ!", Toast.LENGTH_SHORT).show();
                return;
            }
            String prefixID = "", id ="", key="", prefixKey = "IEM";
            String getDepart = departmentSpinner.getSelectedItem().toString();
            if(getDepart.equals("Phát triển pần mềm"))
                prefixID = "SD";
            else if(getDepart.equals("Hạ tầng IT"))
                prefixID = "INF";
            else if(getDepart.equals("An ninh mạng"))
                prefixID = "NS";
            else if(getDepart.equals("Quản lý dự án"))
                prefixID = "PM";
            else if(getDepart.equals("Nhân sự"))
                prefixID = "HR";
            else if(getDepart.equals("Hỗ trợ kỹ thuật"))
                prefixID = "TS";



            Random randID = new Random();
            Random randKey = new Random();
            int randomNumber = randID.nextInt(9000) + 1000;
            int randomKey = randKey.nextInt(90000) + 10000;
            id = prefixID + String.valueOf(randomNumber);
            key = prefixKey + String.valueOf(randomKey);
            EditText idEditText = findViewById(R.id.id_info_emp);
            EditText keyEditText = findViewById(R.id.key_info_emp);
            idEditText.setText(id);
            keyEditText.setText(key);
        });

        // Tạo tài khoản
        Button createAccountButton = findViewById(R.id.create_account_button);
        createAccountButton.setOnClickListener(v -> {
            Toast.makeText(this, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_employee_account), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}