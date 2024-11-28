package ex.g1.iem.ImageButton_Home_Admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

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

import Adapter.SoftWareAdapter;
import Class.Resource;
import Adapter.HardWareAdapter;
import ex.g1.iem.Deep_Event.Create_Resource;
import ex.g1.iem.R;

public class Resource_ImageButton extends AppCompatActivity {
    FirebaseFirestore firestore;
    @SuppressLint("MissingInflatedId")
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

        softwareList.add(new Resource("1","Linux OS", 20, false));
        softwareList.add(new Resource("1","Linux OS", 20, false));
        softwareList.add(new Resource("1","Linux OS", 20, false));
        softwareList.add(new Resource("1","Linux OS", 20, false));


        recyclerViewSoftware.setLayoutManager(new LinearLayoutManager(this));
        SoftWareAdapter softWareAdapter = new SoftWareAdapter(softwareList);
        recyclerViewSoftware.setAdapter(softWareAdapter);

        //todo: Hardware
        RecyclerView recyclerViewHardware = findViewById(R.id.recyclerView_hardware);
        List<Resource> hardwareList = new ArrayList<>();

        hardwareList.add(new Resource("1","Máy tính", 50, true));
        hardwareList.add(new Resource("1","Máy tính", 50, true));
        hardwareList.add(new Resource("1","Máy tính", 50, true));
        hardwareList.add(new Resource("1","Máy tính", 50, true));hardwareList.add(new Resource("1","Máy tính", 50, true));
        hardwareList.add(new Resource("1","Máy tính", 50, true));



        recyclerViewHardware.setLayoutManager(new LinearLayoutManager(this));
        HardWareAdapter hardWareAdapter = new HardWareAdapter(hardwareList);
        recyclerViewHardware.setAdapter(hardWareAdapter);

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