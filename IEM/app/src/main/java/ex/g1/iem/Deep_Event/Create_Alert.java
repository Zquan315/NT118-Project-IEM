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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ex.g1.iem.R;

public class Create_Alert extends AppCompatActivity {
    String usernameAdmin;
    FirebaseFirestore db ;
    EditText idEditText, titleEditText, contentEditText;
    Button addButton;
    ImageButton genID;
    DatabaseReference DBRealtime;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_alert);
        usernameAdmin = getIntent().getStringExtra("username");
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        DBRealtime = FirebaseDatabase.getInstance().getReference();
        idEditText = findViewById(R.id.ID_Alert_EditText);
        titleEditText = findViewById(R.id.title_EditText);
        contentEditText = findViewById(R.id.content_EditText);
        addButton = findViewById(R.id.Create_Alert_Button);
        genID = findViewById(R.id.Generate_ID_Button);
        // TODO: xử lý sự kiện khi nhấn nút thêm thông báo
        genID.setOnClickListener(v -> {
            Random random = new Random();
            int randomNumber = 1000 + random.nextInt(9000);
            String id = "AL" + String.valueOf(randomNumber);
            idEditText.setText(id);
        });
        addButton.setOnClickListener(v -> {
            try {
                if (idEditText.getText().toString().isEmpty()
                        || titleEditText.getText().toString().isEmpty()
                        || contentEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(this, "Vui lòng điền đầy đủ thông tin ! ", Toast.LENGTH_SHORT).show();
                    return;
                }
                //tạo id ngẫu nhiên

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedNow = now.format(formatter);

                String documentID = idEditText.getText().toString() ;
                Map<String, Object> data = new HashMap<>();
                data.put("title", titleEditText.getText().toString());
                data.put("time", formattedNow);
                data.put("content", contentEditText.getText().toString());

                // Thêm document vào collection "Alerrt"
                db.collection("Alert").document(documentID)
                        .set(data)
                        .addOnSuccessListener(aVoid -> {
                            // Thành công
                            Toast.makeText(this,"Thêm Thông Báo Thành Công !", Toast.LENGTH_LONG).show();
                        })
                        .addOnFailureListener(e -> {
                            // Lỗi
                            Toast.makeText(this,"Thêm Thông Báo Thất Bại !", Toast.LENGTH_LONG).show();
                        });
                idEditText.setText("");
                titleEditText.setText("");
                contentEditText.setText("");
            }
            catch (Exception e)
            {
                Toast.makeText(this,"Error", Toast.LENGTH_LONG).show();
            }
        });

        //xử lý nút quay lại
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_alert), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}