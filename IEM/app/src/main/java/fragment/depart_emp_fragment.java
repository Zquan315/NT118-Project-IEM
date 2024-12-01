package fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import Adapter.EmployeeAdapter;
import Class.Employee;
import Adapter.SalaryAdapter;
import ex.g1.iem.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link depart_emp_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class depart_emp_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    String usernameEmp, department;
    public RecyclerView recyclerView;
    public List<Employee> employeeList;
    public EmployeeAdapter employeeAdapter;
    FirebaseFirestore firestore;
    TextView name_depart_emp, num_of_employee;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public depart_emp_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment depart_emp_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static depart_emp_fragment newInstance(String param1, String param2) {
        depart_emp_fragment fragment = new depart_emp_fragment();
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

    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_depart_emp_fragment, container, false);
        assert getArguments() != null;
        usernameEmp = getArguments().getString("username");
        FirebaseApp.initializeApp(this.requireContext());
        firestore = FirebaseFirestore.getInstance();
        name_depart_emp = view.findViewById(R.id.name_depart_emp);
        num_of_employee = view.findViewById(R.id.num_of_employee);
        //todo: load danh sách nhân viên
        recyclerView = view.findViewById(R.id.recyclerView_Depart_emp);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        employeeList = new ArrayList<>();
        employeeAdapter = new EmployeeAdapter(employeeList,usernameEmp);
        recyclerView.setAdapter(employeeAdapter);
        firestore.collection("Employee").document(usernameEmp).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        department = task.getResult().getString("depart");
                        name_depart_emp.setText(department);
                    }
                });
        firestore.collection("Employee")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        String id = document.getId(); // Lấy ID của document
                        if(id.equals("tocongquanmmtt"))
                            continue;
                        String name = document.getString("name");
                        String depart = document.getString("depart");
                        String email = document.getString("email");
                        String gender = document.getString("gender");
                        String key = document.getString("key");
                        String phone = document.getString("phone");
                        String role = document.getString("role");
                        if(depart != null && depart.equals(department))
                            employeeList.add(new Employee(id, name, key, phone, email, depart, gender, role));
                    }
                    // todo: Cập nhật giao diện sau khi lấy dữ liệu thành công
                    employeeAdapter.notifyDataSetChanged();
                    //todo: hiển thị số lượng nhân viên
                    num_of_employee.setText(String.valueOf(employeeList.size()));
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                });

        return view;
    }
}