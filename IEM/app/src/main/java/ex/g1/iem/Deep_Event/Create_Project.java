package ex.g1.iem.Deep_Event;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import Class.ProjectManage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ex.g1.iem.R;

public class Create_Project extends AppCompatActivity {

    String usernameEmp;
    EditText idEditText, nameEditText, underTakeEditText,
    dayEditText, monthEditText, yearEditText, descriptionEditText;
    Button createButton;
    ImageButton genID;
    FirebaseFirestore firestore;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_project);
        usernameEmp = getIntent().getStringExtra("username");
        FirebaseApp.initializeApp(this);
        firestore = FirebaseFirestore.getInstance();
        //todo: khoi tao cac doi tuong
        idEditText = findViewById(R.id.ID_Project_EditText);
        nameEditText = findViewById(R.id.name_project_EditText);
        underTakeEditText = findViewById(R.id.underTake_project_EditText);
        dayEditText = findViewById(R.id.day_EditText);
        monthEditText = findViewById(R.id.month_EditText);
        yearEditText = findViewById(R.id.year_EditText);
        descriptionEditText = findViewById(R.id.description_EditText);
        createButton = findViewById(R.id.Create_Project_Button);
        genID = findViewById(R.id.Generate_ID_PJ_Button);

        //todo: load Dam nhan
        firestore.collection("Employee").document(usernameEmp).get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                String underTake = task.getResult().getString("depart");
                                underTakeEditText.setText(underTake);
                            }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
                });

        //todo: tao id
        genID.setOnClickListener(v -> {
            Random random = new Random();
            int randomNumber = 1000 + random.nextInt(9000);
            String id = "PJ" + String.valueOf(randomNumber);
            idEditText.setText(id);
        });

        //todo: them du an
        createButton.setOnClickListener(v -> {
            String ID = idEditText.getText().toString();
            String name = nameEditText.getText().toString();
            String underTake = underTakeEditText.getText().toString();
            String day = dayEditText.getText().toString();
            String month = monthEditText.getText().toString();
            String year = yearEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            //todo: kiem tra dieu kien
            if(ID.isEmpty() || name.isEmpty() || day.isEmpty()
                    || month.isEmpty() || year.isEmpty() || description.isEmpty())
            {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!isDate(day, month, year))
            {
                Toast.makeText(this, "Ngày không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            String date = day + "/" + month + "/" + year;
            firestore.collection("Project").document(ID).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                idEditText.setError("ID đã tồn tại");
                            }
                            else {
                                ProjectManage project = new ProjectManage(ID, name, underTake, description,date);
                                firestore.collection("Project").document(ID)
                                        .set(project)
                                        .addOnSuccessListener(aVoid ->{
                                                Toast.makeText(this, "Thêm dự án thành công",
                                                        Toast.LENGTH_SHORT).show();
                                                firestore.collection("Department").document(underTake)
                                                                .update("amount_pj", FieldValue.increment(1));
                                                idEditText.setText("");
                                                nameEditText.setText("");
                                                dayEditText.setText("");
                                                monthEditText.setText("");
                                                yearEditText.setText("");
                                                descriptionEditText.setText("");
                                            }
                                        )
                                        .addOnFailureListener(e ->
                                                Toast.makeText(this, "Thêm dự án thất bại", Toast.LENGTH_SHORT).show()
                                        );
                            }
                        }
                    });
        });

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_project), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    boolean isDate(String d, String m, String y)
    {
        int day = Integer.parseInt(d);
        int month = Integer.parseInt(m);
        int year = Integer.parseInt(y);

        if(isLeapYear(year))
        {
            if(month == 2)
                return day <= 29;
            if( month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
                return day <= 31;
            return day <= 30;
        }
        else {
            if(month == 2)
                return day <= 28;
            if( month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
                return day <= 31;
            return day <= 30;
        }
    }

    boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}