package fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public RecyclerView recyclerView;
    public List<Employee> employeeList;
    public EmployeeAdapter employeeAdapter;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_depart_emp_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_Depart_emp);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        employeeList = new ArrayList<>();
//        employeeList.add(new Employee("Tô Công Quân", "IT", "Nhân viên", "1190"));
//        employeeList.add(new Employee("Nguyễn Thành Thạo", "IT", "Nhân viên", "1371"));
//        employeeList.add(new Employee("Lâm Hoàng Phước", "IT", "Nhân viên", "1153"));
//        employeeList.add(new Employee("Huỳnh Ngọc Anh Kiệt", "IT", "Nhân viên", "0718"));


        employeeAdapter = new EmployeeAdapter(employeeList);
        recyclerView.setAdapter(employeeAdapter);

        return view;
    }
}