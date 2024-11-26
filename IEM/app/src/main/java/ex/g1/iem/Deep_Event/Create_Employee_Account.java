package ex.g1.iem.Deep_Event;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import Class.Employee;

import java.util.Objects;
import java.util.Random;
import Class.SalaryManagement;
import ex.g1.iem.R;

public class Create_Employee_Account extends AppCompatActivity {
    FirebaseFirestore firestore;
    DatabaseReference DBRealtime;
    EditText nameEditText, idEditText, keyEditText, phoneEditText, emailEditText;
    Spinner departmentSpinner, genderSpinner, roleSpinner;
    Button createAccount;
    @SuppressLint({"MissingInflatedId", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_employee_account);
        FirebaseApp.initializeApp(this);
        firestore = FirebaseFirestore.getInstance();
        DBRealtime = FirebaseDatabase.getInstance().getReference();

        nameEditText = findViewById(R.id.name_info_emp);
        idEditText = findViewById(R.id.id_info_emp);
        keyEditText = findViewById(R.id.key_info_emp);
        phoneEditText = findViewById(R.id.phone_info_emp);
        emailEditText = findViewById(R.id.email_info_emp);
        // Spinner for Phòng ban
        departmentSpinner = findViewById(R.id.depart_info_emp);
        ArrayAdapter<CharSequence> departmentAdapter = ArrayAdapter.createFromResource(this,
                R.array.departments_array, R.layout.spinner_item);
        departmentAdapter.setDropDownViewResource( R.layout.spinner_dropdown_item);
        departmentSpinner.setAdapter(departmentAdapter);
        departmentSpinner.setSelection(0);

        // Spinner for Giới tính
        genderSpinner = findViewById(R.id.sex_info_emp);
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, R.layout.spinner_item);
        genderAdapter.setDropDownViewResource( R.layout.spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setSelection(0);

        //spinner chức vụ
        roleSpinner = findViewById(R.id.role_info_emp);
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
            if(getDepart.equals("Phát triển phần mềm"))
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
            idEditText.setText(id);
            keyEditText.setText(key);
        });


        // Tạo tài khoản va lưu thông tin lên database
        createAccount = findViewById(R.id.create_account_button);
        createAccount.setOnClickListener(v -> {
            if(nameEditText.getText().toString().isEmpty() || idEditText.getText().toString().isEmpty()
            || keyEditText.getText().toString().isEmpty() || departmentSpinner.getSelectedItem().toString().equals("None")
            || genderSpinner.getSelectedItem().toString().equals("None")
            || roleSpinner.getSelectedItem().toString().equals("None"))
            {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = nameEditText.getText().toString();
            String id = idEditText.getText().toString();
            String key = keyEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String department = departmentSpinner.getSelectedItem().toString();
            String gender = genderSpinner.getSelectedItem().toString();
            String role = roleSpinner.getSelectedItem().toString();

            try {
                checkIfUsernameExists(id, exists -> {
                    if (exists) {
                        Toast.makeText(this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                        return;}
                // Lưu thông tin vào Firebase Realtime Database
                addEmpToFirebaseRealtime(id);

                // Lưu thông tin vào Firestore
                Employee employee = new Employee(name, id, key, phone, email, department, gender, role);
                addEmptoFireStore(employee);
                addToSalary(id, name, role);
                add_numEmptoDepartment(department);
                //reset form
                nameEditText.setText("");
                idEditText.setText("");
                keyEditText.setText("");
                phoneEditText.setText("");
                emailEditText.setText("");
                departmentSpinner.setSelection(0);
                genderSpinner.setSelection(0);
                roleSpinner.setSelection(0);

                Toast.makeText(this, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();
                });
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Tạo tài khoản thất bại!", Toast.LENGTH_SHORT).show();
            }

        });


        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_employee_account), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

     void addEmpToFirebaseRealtime(String id){
        DBRealtime.child("Account").child(id).child("password").setValue("12345678");
        }
     void addEmptoFireStore(Employee e)
     {
         firestore.collection("Employee").document(e.getId()).set(e);
     }
     void addToSalary(String id, String name, String role)
     {
         firestore.collection("Salary").document(id).set(
                 new SalaryManagement(id, name, Objects.equals(role, "Trưởng phòng") ?"20000000":"15000000", 0, "0"));
     }
     void add_numEmptoDepartment(String department)
     {
         firestore.collection("Department").document(department).update("amount_emp", FieldValue.increment(1));
     }

    public void checkIfUsernameExists(String id, OnUsernameCheckListener listener) {
        DBRealtime.child("Account").child(id).child("password").get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful() && task.getResult().exists()) {
                            listener.onUsernameExists(true);  // Tồn tại
                        } else {
                            listener.onUsernameExists(false); // Không tồn tại hoặc có lỗi
                        }
                    }
                });
    }

    public interface OnUsernameCheckListener {
        void onUsernameExists(boolean exists);
    }

}