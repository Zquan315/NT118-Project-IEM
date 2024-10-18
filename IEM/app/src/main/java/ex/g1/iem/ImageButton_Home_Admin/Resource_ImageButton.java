package ex.g1.iem.ImageButton_Home_Admin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapter.SoftWareAdapter;
import Class.Resource;
import Adapter.HardWareAdapter;
import ex.g1.iem.R;

public class Resource_ImageButton extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resource_image_button);


        //Software
        RecyclerView recyclerViewSoftware = findViewById(R.id.recyclerView_software);
        List<Resource> softwareList = new ArrayList<>();

        softwareList.add(new Resource("Linux OS", 20, false));
        softwareList.add(new Resource("Windows OS", 10, false));
        softwareList.add(new Resource("Mac OS", 5, false));

        recyclerViewSoftware.setLayoutManager(new LinearLayoutManager(this));
        SoftWareAdapter softWareAdapter = new SoftWareAdapter(softwareList);
        recyclerViewSoftware.setAdapter(softWareAdapter);

        //Hardware
        RecyclerView recyclerViewHardware = findViewById(R.id.recyclerView_hardware);
        List<Resource> hardwareList = new ArrayList<>();

        hardwareList.add(new Resource("Máy tính", 50, true));
        hardwareList.add(new Resource("PC", 30, true));
        hardwareList.add(new Resource("Laptop", 20, true));
        hardwareList.add(new Resource("Router", 12, true));
        hardwareList.add(new Resource("Switch", 22, true));

        recyclerViewHardware.setLayoutManager(new LinearLayoutManager(this));
        HardWareAdapter hardWareAdapter = new HardWareAdapter(hardwareList);
        recyclerViewHardware.setAdapter(hardWareAdapter);


        findViewById(R.id.backButton);
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.resource_imagebutton), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}