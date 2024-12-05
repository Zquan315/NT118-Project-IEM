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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import Adapter.ProjectManageAdapter;
import Class.ProjectManage;
import Adapter.ProjectManageAdapter;
import ex.g1.iem.Deep_Event.Create_Project;
import ex.g1.iem.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link project_emp_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class project_emp_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    String usernameEmp;
    String department;
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

    @SuppressLint("NotifyDataSetChanged")
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
        ProjectManageAdapter = new ProjectManageAdapter(projectManageList, usernameEmp);
        recyclerView.setAdapter(ProjectManageAdapter);
        FirebaseApp.initializeApp(this.requireContext());
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FloatingActionButton fab = view.findViewById(R.id.fab_add_project);
        //todo: lay phong ban
        firestore.collection("Employee").document(usernameEmp).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult().exists())
                    {
                        department = task.getResult().getString("depart");
                    }
                });
        //todo: chi Truong phong moi thay nut FAB
        firestore.collection("Employee").document(usernameEmp).get()
                        .addOnCompleteListener(task -> {
                            String role = task.getResult().getString("role");
                            if (task.isSuccessful()) {
                                if(role != null && role.equals("Trưởng phòng"))
                                {
                                   fab.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    fab.setVisibility(View.GONE);
                                }
                            }
                            else {
                                Toast.makeText(this.requireContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                            }
                        });

        //todo: load danh sach du an
        firestore.collection("Project")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        String id = document.getId();
                        if(id.equals("id"))
                            continue;
                        String description = document.getString("description");
                        String name = document.getString("name");
                        String underTake = document.getString("underTake");
                        String deadline = document.getString("deadline");
                        if(department.equals(underTake))
                            projectManageList.add(new ProjectManage(id, name,underTake, description , deadline));

                    }
                    // Cập nhật giao diện sau khi lấy dữ liệu thành công
                    ProjectManageAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this.requireContext(), "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                });

        //todo: Tao du an
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(project_emp_fragment.this.requireContext(), Create_Project.class);
            intent.putExtra("username", usernameEmp);
            startActivity(intent);
        });

        return view;   
    }
}