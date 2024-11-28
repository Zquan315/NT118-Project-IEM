package ex.g1.iem.ImageButton_Home_Admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Adapter.SoftWareAdapter;
import Class.Resource;
import Adapter.HardWareAdapter;
import ex.g1.iem.Deep_Event.Create_Resource;
import ex.g1.iem.R;

public class Resource_ImageButton extends AppCompatActivity {
    FirebaseFirestore firestore;
    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resource_image_button);
        FirebaseApp.initializeApp(this);
        firestore = FirebaseFirestore.getInstance();

        //todo: Software
        RecyclerView recyclerViewSoftware = findViewById(R.id.recyclerView_software);
        List<Resource> softwareList = new ArrayList<>();
        recyclerViewSoftware.setLayoutManager(new LinearLayoutManager(this));
        SoftWareAdapter softWareAdapter = new SoftWareAdapter(softwareList);
        recyclerViewSoftware.setAdapter(softWareAdapter);

        //todo: load SW
        firestore.collection("Res").document("SW").get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> fields = documentSnapshot.getData();
                        if (fields != null) {
                            for (Map.Entry<String, Object> entry : fields.entrySet())
                            {
                                String fieldID = entry.getKey();
                                Object fieldValue = entry.getValue();

                                //todo: kiểm tra nếu là mảng
                                if (fieldValue instanceof List) {
                                    List<?> array = (List<?>) fieldValue;
                                    String name = (String) array.get(0);
                                    String type = (String) array.get(1);
                                    String amount = (String) array.get(2);
                                    int amountInt = Integer.parseInt(amount);
                                    Resource resource = new Resource(fieldID, name, amountInt,type.equals("Phần cứng")?true:false);
                                    softwareList.add(resource);
                                    softWareAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                        else {
                            Toast.makeText(this, "Không tìm thấy tài nguyên", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(this, "Không tìm thấy tài nguyên", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi khi load tài nguyên", Toast.LENGTH_SHORT).show();
                });

        //todo: Hardware
        RecyclerView recyclerViewHardware = findViewById(R.id.recyclerView_hardware);
        List<Resource> hardwareList = new ArrayList<>();
        recyclerViewHardware.setLayoutManager(new LinearLayoutManager(this));
        HardWareAdapter hardWareAdapter = new HardWareAdapter(hardwareList);
        recyclerViewHardware.setAdapter(hardWareAdapter);

        firestore.collection("Res").document("HW").get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> fields = documentSnapshot.getData();
                        if (fields != null) {
                            for (Map.Entry<String, Object> entry : fields.entrySet())
                            {
                                String fieldID = entry.getKey();
                                Object fieldValue = entry.getValue();

                                //todo: kiểm tra nếu là mảng
                                if (fieldValue instanceof List) {
                                    List<?> array = (List<?>) fieldValue;
                                    String name = (String) array.get(0);
                                    String type = (String) array.get(1);
                                    String amount = (String) array.get(2);
                                    int amountInt = Integer.parseInt(amount);
                                    Resource resource = new Resource(fieldID, name, amountInt,type.equals("Phần cứng")?true:false);
                                    hardwareList.add(resource);
                                    hardWareAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                        else {
                            Toast.makeText(this, "Không tìm thấy tài nguyên", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(this, "Không tìm thấy tài nguyên", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi khi load tài nguyên", Toast.LENGTH_SHORT).show();
                });


        //todo: Floating Action Button
        findViewById(R.id.fab).setOnClickListener(v -> {
            startActivity(new Intent(Resource_ImageButton.this, Create_Resource.class));
        });

        //todo: back button
        findViewById(R.id.backButton);
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.resource_imagebutton), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}