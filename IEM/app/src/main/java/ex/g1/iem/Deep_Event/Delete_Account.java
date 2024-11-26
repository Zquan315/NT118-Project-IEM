package ex.g1.iem.Deep_Event;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
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

import java.util.concurrent.atomic.AtomicBoolean;

import ex.g1.iem.MainActivity;
import ex.g1.iem.R;

public class Delete_Account extends AppCompatActivity {
    FirebaseFirestore firestore;
    DatabaseReference DBRealtime;
    String ID;
    EditText IDEditText;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_account);
        FirebaseApp.initializeApp(this);
        firestore = FirebaseFirestore.getInstance();
        DBRealtime = FirebaseDatabase.getInstance().getReference();
        //todo: Xóa tài khoản
        IDEditText = findViewById(R.id.IDEditText);
        findViewById(R.id.confirmButton).setOnClickListener(v -> {
            ID = IDEditText.getText().toString();
            if (ID.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập mã nhân viên", Toast.LENGTH_SHORT).show();
                return;
            }

            checkExistDBRealtime(ID, existsInRealtime -> {
                if (!existsInRealtime) {
                    IDEditText.setError("Tài khoản không tồn tại");
                    return;
                }

                checkExistFirestore(ID, existsInFirestore -> {
                    if (!existsInFirestore) {
                        IDEditText.setError("Tài khoản không tồn tại");
                        return;
                    }

                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("Xóa tài khoản");
                    dialog.setMessage("Bạn có chắc chắn muốn xóa nhân viên: " + ID + " không?");
                    dialog.setPositiveButton("OK", (dialog1, which) -> {
                        DBRealtime.child("Account").child(ID).removeValue();
                        firestore.collection("Employee").document(ID).delete();
                        firestore.collection("Salary").document(ID).delete();
                        Toast.makeText(Delete_Account.this, "Xóa tài khoản thành công", Toast.LENGTH_SHORT).show();
                        IDEditText.setText("");
                    });
                    dialog.setNegativeButton("Hủy", (dialog1, which) -> dialog1.dismiss());
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                });
            });
        });

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.delete_account), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void checkExistDBRealtime(String ID, ExistCallback callback) {
        DBRealtime.child("Account").child(ID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                callback.onResult(true);
            } else {
                callback.onResult(false);
            }
        });
    }

    void checkExistFirestore(String ID, ExistCallback callback) {
        firestore.collection("Employee").document(ID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                callback.onResult(true);
            } else {
                callback.onResult(false);
            }
        });
    }
    interface ExistCallback {
        void onResult(boolean exists);
    }
}