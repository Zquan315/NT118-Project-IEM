package fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Adapter.SalaryAdapter;
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_admin_fragment, container, false);


        recyclerView = view.findViewById(R.id.recyclerView_mana);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        employeeList = new ArrayList<>();
        employeeList.add(new SalaryManagement("Tô Công Quân", "20,000,000 VND", 0, "20,000,000 VND"));
        employeeList.add(new SalaryManagement("Nguyễn Thành Thạo", "15,000,000 VND", 1, "14,750,000 VND"));
        employeeList.add(new SalaryManagement("Lâm Hoàng Phước", "12,500,000 VND", 4, "11,500,000 VND"));
        employeeList.add(new SalaryManagement("Huỳnh Ngọc Anh Kiệt", "12,500,000 VND", 2, "12,000,000 VND"));
        employeeList.add(new SalaryManagement("Lê Hoàng Nam", "12,500,000 VND", 1, "12,250,000 VND"));

        salaryAdapter = new SalaryAdapter(employeeList);
        recyclerView.setAdapter(salaryAdapter);
        return view;
    }
}