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

import Adapter.ProjectManageAdapter;
import Class.ProjectManage;
import Adapter.ProjectManageAdapter;
import ex.g1.iem.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link project_emp_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class project_emp_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    String usernameEmp;
    public RecyclerView recyclerView;
    public List<ProjectManage> projectManageList;
    public ProjectManageAdapter ProjectManageAdapter;
    
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public project_emp_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment project_emp_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static project_emp_fragment newInstance(String param1, String param2) {
        project_emp_fragment fragment = new project_emp_fragment();
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
        View view = inflater.inflate(R.layout.fragment_project_emp_fragment, container, false);
        assert getArguments() != null;
        usernameEmp = getArguments().getString("username");


        recyclerView = view.findViewById(R.id.recyclerView_project_emp);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        projectManageList = new ArrayList<>();
        for (int i = 1; i < 6; i++)
        {
            int a = i + 90;
            projectManageList.add(new ProjectManage("00" + Integer.toString(i),
                    "IEM" + Integer.toString(i*5), "IT"));
        }
        ProjectManageAdapter = new ProjectManageAdapter(projectManageList);
        recyclerView.setAdapter(ProjectManageAdapter);
        
        return view;   
    }
}