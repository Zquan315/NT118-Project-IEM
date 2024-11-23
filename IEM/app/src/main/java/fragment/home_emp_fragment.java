package fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import Adapter.AlertAdapter;
import ex.g1.iem.R;
import Class.Alert;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home_emp_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home_emp_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    String usernameEmp;
    RecyclerView recyclerView;
    AlertAdapter alertAdapter;
    ArrayList<Alert> alertList;
    FirebaseFirestore firestore;
    DatabaseReference DBRealtime;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home_emp_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_emp_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static home_emp_fragment newInstance(String param1, String param2) {
        home_emp_fragment fragment = new home_emp_fragment();
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
        View view = inflater.inflate(R.layout.fragment_home_emp_fragment, container, false);
        assert getArguments() != null;
        usernameEmp = getArguments().getString("username");
        FirebaseApp.initializeApp(this.requireActivity());
        firestore = FirebaseFirestore.getInstance();
        DBRealtime = FirebaseDatabase.getInstance().getReference();

        //todo: hiển thị thông báo
        alertList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            alertList.add(new Alert("ID" + i, "Thông báo " + (i + 1)));
        }
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        alertAdapter = new AlertAdapter(alertList);
        recyclerView.setAdapter(alertAdapter);
        // todo: end

        return view;
    }
}