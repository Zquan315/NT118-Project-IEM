package fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import Adapter.SalaryAdapter;
import ex.g1.iem.Deep_Event.Salary_Edit;
import ex.g1.iem.R;
import Class.SalaryManagement;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link manage_admin_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class manage_admin_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    public RecyclerView recyclerView;
    public  List<SalaryManagement> employeeList;
    public SalaryAdapter salaryAdapter;
    FirebaseFirestore firestore;
    DatabaseReference DBRealtime;
    EditText searchEditText;
    Button searchButton;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public manage_admin_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment manage_admin_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static manage_admin_fragment newInstance(String param1, String param2) {
        manage_admin_fragment fragment = new manage_admin_fragment();
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
        View view = inflater.inflate(R.layout.fragment_manage_admin_fragment, container, false);
        FirebaseApp.initializeApp(this.requireContext());
        firestore = FirebaseFirestore.getInstance();
        DBRealtime = FirebaseDatabase.getInstance().getReference();
        searchEditText = view.findViewById(R.id.search_EditText);
        searchButton = view.findViewById(R.id.search_button);
        recyclerView = view.findViewById(R.id.recyclerView_mana);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        employeeList = new ArrayList<>();
        salaryAdapter = new SalaryAdapter(employeeList);
        recyclerView.setAdapter(salaryAdapter);
        //todo: load danh sách lương
        firestore.collection("Salary")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        String id = document.getId(); // Lấy ID của document
                        if(id.equals("id"))
                            continue;
                        String name = document.getString("name");
                        String basicSalary = document.getString("basicSalary");
                        Long leaveDays = document.getLong("leaveDays");
                        String totalSalary = document.getString("totalSalary");
                        int leaveDaysInt = (leaveDays != null) ? leaveDays.intValue() : 0;
                        employeeList.add(new SalaryManagement(
                                id,name,basicSalary,leaveDaysInt,totalSalary ));
                    }
                    // Cập nhật giao diện sau khi lấy dữ liệu thành công
                    salaryAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                });
        //todo: Tìm kiếm
        searchButton.setOnClickListener(v -> {
            String searchText = searchEditText.getText().toString().trim();
            List<SalaryManagement> filteredList = new ArrayList<>();

            if (searchText.isEmpty()) {
                filteredList.addAll(employeeList);
            } else {
                for (SalaryManagement employee : employeeList) {
                    if (employee.getId().toLowerCase().contains(searchText.toLowerCase())) {
                        filteredList.add(employee);
                    }
                }
            }

            salaryAdapter = new SalaryAdapter(filteredList);
            recyclerView.setAdapter(salaryAdapter);
            salaryAdapter.notifyDataSetChanged();

            if (filteredList.isEmpty()) {
                Toast.makeText(getContext(), "Không tìm thấy kết quả", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}