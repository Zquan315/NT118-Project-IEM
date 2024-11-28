package ex.g1.iem.Deep_Event;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ex.g1.iem.R;

public class Create_Resource extends AppCompatActivity {
    FirebaseFirestore firestore;
    EditText idEditText, nameEditText, amountEditText;
    Spinner typeSpinner;
    Button addButton;
    ImageButton genID;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_resource);
        FirebaseApp.initializeApp(this);
        firestore = FirebaseFirestore.getInstance();
        idEditText = findViewById(R.id.ID_Resource_EditText);
        nameEditText = findViewById(R.id.name_Resource_EditText);
        amountEditText = findViewById(R.id.amount_resource_EditText);
        typeSpinner = findViewById(R.id.type_resource_spinner);
        addButton = findViewById(R.id.create_resource_Button);
        genID = findViewById(R.id.generate_ID_Button);

        //todo: set up spinner
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.resource_array,R.layout.spinner_item);
        typeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setSelection(0);

        //todo: Generate ID
        genID.setOnClickListener(v -> {
            //todo: tạo id ngẫu nhiên
            String prefix = "";
            if(typeSpinner.getSelectedItem().toString().equals("None"))
            {
                Toast.makeText(this,"Vui lòng chọn loại tài nguyên !", Toast.LENGTH_LONG).show();
                return;
            }
            else if(typeSpinner.getSelectedItem().toString().equals("Phần cứng"))
            {
                prefix = "HW";
            }
            else
                prefix = "SW";
            Random random = new Random();
            int randomNumber = 1000 + random.nextInt(9000);
            String id = prefix + String.valueOf(randomNumber);
            idEditText.setText(id);
            typeSpinner.setEnabled(false);
        });

        //todo: add resource
        addButton.setOnClickListener(v -> {
            try {
                if (idEditText.getText().toString().isEmpty()
                        || nameEditText.getText().toString().isEmpty()
                        || amountEditText.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra tính hợp lệ của số lượng
                try {
                    int amountInt = Integer.parseInt(amountEditText.getText().toString());
                    if (amountInt <= 0) {
                        Toast.makeText(this, "Số lượng phải là số nguyên dương!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Số lượng phải là số nguyên dương!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Chuẩn bị dữ liệu
                String id = idEditText.getText().toString();
                String type = typeSpinner.getSelectedItem().toString();
                String name = nameEditText.getText().toString();
                int amount = Integer.parseInt(amountEditText.getText().toString());
                List<String> itemArray = Arrays.asList(name, type, String.valueOf(amount));

                Map<String, Object> data = new HashMap<>();
                data.put(id, itemArray);

                String collection = type.equals("Phần cứng") ? "HW" : "SW";

                firestore.collection("Res").document(collection)
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.contains(id)) {
                                Toast.makeText(this, "ID này đã tồn tại!", Toast.LENGTH_SHORT).show();
                            } else {
                                // Thêm tài nguyên mới
                                firestore.collection("Res").document(collection)
                                        .set(data, SetOptions.merge())
                                        .addOnSuccessListener(aVoid -> {
                                            idEditText.setText("");
                                            nameEditText.setText("");
                                            amountEditText.setText("");
                                            typeSpinner.setSelection(0);
                                            Toast.makeText(this, "Thêm tài nguyên thành công!", Toast.LENGTH_LONG).show();
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(this, "Thêm tài nguyên thất bại!", Toast.LENGTH_LONG).show());
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Lỗi khi kiểm tra ID!", Toast.LENGTH_SHORT).show();
                        });
            }
            catch (Exception e) {
                // Ghi thông tin lỗi vào logcat
                Log.e("CreateResourceError", "Lỗi không xác định", e);
                Toast.makeText(this, "Lỗi không xác định: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        });


        //xử lý nút quay lại
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_resource), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}