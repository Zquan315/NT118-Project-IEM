package ex.g1.iem.Deep_Event;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.Objects;

import ex.g1.iem.MainActivity;
import ex.g1.iem.R;

public class Info_Employee extends AppCompatActivity {
    DatabaseReference DBRealtime;
    String username;
    FirebaseFirestore firestore;
    boolean upRole = false;
    boolean downRole = false;
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info_employee);
        FirebaseApp.initializeApp(this);
        DBRealtime = FirebaseDatabase.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();
        username = getIntent().getStringExtra("username");

        //TODO: Update with your layout
        TextView name = findViewById(R.id.name_TextView);
        TextView id = findViewById(R.id.id_TextView);
        EditText depart = findViewById(R.id.depart_info);
        EditText phone = findViewById(R.id.phone_info);
        EditText email = findViewById(R.id.email_info);
        EditText gender = findViewById(R.id.sex_info);
        EditText role = findViewById(R.id.role_info);
        String ph = getIntent().getStringExtra("phone");
        String e = getIntent().getStringExtra("email");
        String r = getIntent().getStringExtra("role");
        String id_ = getIntent().getStringExtra("id");
        String depart_ = getIntent().getStringExtra("depart");

        name.setText(getIntent().getStringExtra("name"));
        id.setText(id_);
        depart.setText(depart_);
        phone.setText(Objects.equals(ph, "") ? "Trống" : ph);
        email.setText(Objects.equals(e, "") ? "Trống" : e);
        gender.setText(getIntent().getStringExtra("gender"));
        role.setText(r);


        //TODO: Set Text Thang chuc hoac giang chuc
        assert r != null;
        Button changeRole = findViewById(R.id.ChangeRole_Button);
        if(r.equals("Trưởng phòng")) {
            changeRole.setText("Giáng chức");
            downRole = true;
        }
        else {
            changeRole.setText("Thăng chức");
            upRole = true;
        }

        // todo: cho co admin moi duoc thay doi role
        if(username.equals("admin"))
            changeRole.setVisibility(View.VISIBLE);
        else
            changeRole.setVisibility(View.GONE);

        //todo: Cập nhật chức khi thay thăng và giáng
        changeRole.setOnClickListener(v -> {
            try {
                assert id_ != null;
                assert depart_ != null;
                if (upRole) {
                    firestore.collection("Department").document(depart_).get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    String currentHeader = task.getResult().getString("id_header");
                                    if (currentHeader != null && !currentHeader.isEmpty()) {
                                        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                                        dialog.setTitle("Thông báo");
                                        dialog.setMessage(currentHeader + " là trưởng phòng trong phòng ban " + depart_);
                                        dialog.setNegativeButton("OK", (dialog1, which) -> dialog1.dismiss());
                                        dialog.create().show();
                                    }
                                    else {
                                        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                                        dialog.setTitle("Thông báo");
                                        dialog.setMessage("Bạn có chắc chắn muốn thăng chức nhân viên này?");

                                        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                firestore.collection("Employee").document(id_).update("role", "Trưởng phòng")
                                                        .addOnSuccessListener(aVoid -> {
                                                            firestore.collection("Salary").document(id_).update("basicSalary", "20000000");
                                                            firestore.collection("Department").document(depart_).update("id_header", id_);
                                                            Toast.makeText(Info_Employee.this, "Thăng chức thành công", Toast.LENGTH_SHORT).show();
                                                        })
                                                        .addOnFailureListener(e -> Toast.makeText(Info_Employee.this, "Lỗi khi thăng chức", Toast.LENGTH_SHORT).show());
                                            }
                                        });
                                        dialog.setNegativeButton("Hủy", (dialog12, which) -> dialog12.dismiss());
                                        dialog.create().show();
                                    }
                                }
                                else {
                                    Toast.makeText(this, "Không thể truy xuất dữ liệu phòng ban", Toast.LENGTH_SHORT).show();
                                }
                            });
                    return;
                }


                if (downRole) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("Thông báo");
                    dialog.setMessage("Bạn có chắc chắn muốn giáng chức nhân viên này?");

                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            firestore.collection("Employee").document(id_).update("role", "Nhân viên")
                                    .addOnSuccessListener(aVoid -> {
                                        firestore.collection("Salary").document(id_).update("basicSalary", "15000000");
                                        firestore.collection("Department").document(depart_).update("id_header", "");
                                        Toast.makeText(Info_Employee.this, "Giáng chức thành công", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(Info_Employee.this, "Lỗi khi giáng chức", Toast.LENGTH_SHORT).show());
                        }
                    });
                    dialog.setNegativeButton("Hủy", (dialog12, which) -> dialog12.dismiss());
                    dialog.create().show();
                }

            } catch (Exception ex) {
                Toast.makeText(this, "Lỗi khi thay đổi chức vụ", Toast.LENGTH_SHORT).show();
            }
        });


        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.info_employee), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}