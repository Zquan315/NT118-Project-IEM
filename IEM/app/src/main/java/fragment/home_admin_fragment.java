package fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import Adapter.AlertAdapter;
import ex.g1.iem.ImageButton_Home_Admin.Depart_ImageButton;
import ex.g1.iem.ImageButton_Home_Admin.Employee_ImageButton;
import ex.g1.iem.ImageButton_Home_Admin.Project_ImageButton;
import ex.g1.iem.ImageButton_Home_Admin.Resource_ImageButton;
import ex.g1.iem.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home_admin_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home_admin_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match

    private RecyclerView recyclerView;
    private AlertAdapter alertAdapter;
    private List<String> alertList;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home_admin_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_admin_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static home_admin_fragment newInstance(String param1, String param2) {
        home_admin_fragment fragment = new home_admin_fragment();
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
        View view =  inflater.inflate(R.layout.fragment_home_admin_fragment, container, false);

        //Xử lí sự kiện khi nhấn các ImageButton
        ImageButton departImageButton = view.findViewById(R.id.departImageButton);
        ImageButton employeeImageButton = view.findViewById(R.id.employeeImageButton);
        ImageButton planImageButton = view.findViewById(R.id.planImageButton);
        ImageButton resImageButton = view.findViewById(R.id.resImageButton);
        ImageButton financeImageButton = view.findViewById(R.id.financeImageButton);

        departImageButton.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), Depart_ImageButton.class);
            startActivity(intent);
        });
        employeeImageButton.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), Employee_ImageButton.class);
            startActivity(intent);
        });
        planImageButton.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), Project_ImageButton.class);
            startActivity(intent);
        });
        resImageButton.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), Resource_ImageButton.class);
            startActivity(intent);
        });

        // Hiển thị các thông báo
        alertList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            alertList.add("Thông báo " + (i + 1));
        }
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        alertAdapter = new AlertAdapter(alertList);
        recyclerView.setAdapter(alertAdapter);
        return view;
    }


}

