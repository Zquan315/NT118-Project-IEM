package ex.g1.iem.Deep_Event;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

        //todo: Cập nhật chức khi thay thăng và giáng
//        changeRole.setOnClickListener(v -> {
//            assert id_ != null;
//            assert depart_ != null;
//            if(upRole) {
//                String CurrentHeader = firestore.collection("Department").document(depart_).
//                        get().getResult().getString("id_header");
//                if(CurrentHeader != null && !CurrentHeader.isEmpty())
//                {
//                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//                    dialog.setTitle("Thông báo");
//                    dialog.setMessage(id_ + " đang là trưởng phòng của phòng ban "+ depart_);
//
//                    dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    AlertDialog alertDialog = dialog.create();
//                    alertDialog.show();
//                }
//                else {
//                    firestore.collection("Employee").document(id_).update("role", "Trưởng phòng");
//                    firestore.collection("Salary").document(id_).update("basicSalary", "20000000");
//                    firestore.collection("Department").document(depart_).update("id_header", id_);
//                }
//                return;
//            }
//
//            if(downRole) {
//
//                firestore.collection("Employee").document(id_).update("role", "Nhân viên");
//                firestore.collection("Salary").document(id_).update("basicSalary", "15000000");
//                firestore.collection("Department").document(depart_).update("id_header", "");
//                Toast.makeText(this, "Giáng chức thành công", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        });


        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.info_employee), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}