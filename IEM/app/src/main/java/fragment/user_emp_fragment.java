package fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import Class.Employee;
import ex.g1.iem.Deep_Event.Change_Security_Emp;
import ex.g1.iem.MainActivity;
import ex.g1.iem.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link user_emp_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user_emp_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    String usernameEmp;
    FirebaseFirestore firestore;
    DatabaseReference DBRealtime;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public user_emp_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user_emp_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static user_emp_fragment newInstance(String param1, String param2) {
        user_emp_fragment fragment = new user_emp_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_emp_fragment, container, false);
        assert getArguments() != null;
        usernameEmp = getArguments().getString("username");
        FirebaseApp.initializeApp(this.requireActivity());
        firestore = FirebaseFirestore.getInstance();
        DBRealtime = FirebaseDatabase.getInstance().getReference();
        EditText nameEditText = view.findViewById(R.id.name_info_emp);
        EditText idEditText = view.findViewById(R.id.id_info_emp);
        EditText keyEditText = view.findViewById(R.id.key_info_emp);
        EditText phoneEditText = view.findViewById(R.id.phone_info_emp);
        EditText emailEditText = view.findViewById(R.id.email_info_emp);
        EditText departmentEditText = view.findViewById(R.id.depart_info_emp);
        EditText genderEditText = view.findViewById(R.id.sex_info_emp);
        EditText roleEditText = view.findViewById(R.id.role_info_emp);
        //Todo: Load dữ liệu và thay đổi thông tin
        //Load dữ liệu

        try {
            getEmpFromFireStore(usernameEmp, new EmployeeCallback() {
                @Override
                public void onCallback(Employee employee) {
                    if (employee != null) {
                        nameEditText.setText(employee.getName());
                        idEditText.setText(employee.getId());
                        keyEditText.setText(employee.getKey());
                        phoneEditText.setText(employee.getPhone());
                        emailEditText.setText(employee.getEmail());
                        departmentEditText.setText(employee.getDepart());
                        genderEditText.setText(employee.getGender());
                        roleEditText.setText(employee.getRole());
                    } else {
                        Toast.makeText(getActivity(), "Không tìm thấy thông tin nhân viên", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
       catch (Exception e)
       {
           Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
       }

        //ToDo: Lưu thông tin
        Button save_info = view.findViewById(R.id.save_info_button);


        Button change_security_emp = view.findViewById(R.id.change_secure_button);
        change_security_emp.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), Change_Security_Emp.class);
            intent.putExtra("username", usernameEmp);
            startActivity(intent);
        });

        ImageButton logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this.getActivity());
            dialog.setTitle("Đăng xuất");
            dialog.setMessage("Bạn có chắc chắn muốn đăng xuất không?");

            // Nút OK
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    requireActivity().finish();
                }
            });
            // Nút hủy
            dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        });
        return view;

    }
    public interface EmployeeCallback {
        void onCallback(Employee employee);
    }
    void getEmpFromFireStore(String documentId, EmployeeCallback callback) {
        firestore.collection("Employee").document(documentId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Employee employee = documentSnapshot.toObject(Employee.class);
                        callback.onCallback(employee);
                    } else {
                        System.out.println("Document does not exist!");
                        callback.onCallback(null);  // Trả về null nếu không tìm thấy tài liệu
                    }
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error retrieving document: " + e.getMessage());
                    callback.onCallback(null);
                });
    }


}