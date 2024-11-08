package fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;

import ex.g1.iem.Deep_Event.Change_Security_Admin;
import ex.g1.iem.Deep_Event.Create_Employee_Account;
import ex.g1.iem.Deep_Event.Delete_Account;
import ex.g1.iem.MainActivity;
import ex.g1.iem.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link setting_admin_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class setting_admin_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    String usernameAdmin;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public setting_admin_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment setting_admin_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static setting_admin_fragment newInstance(String param1, String param2) {
        setting_admin_fragment fragment = new setting_admin_fragment();
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
        View view = inflater.inflate(R.layout.fragment_setting_admin_fragment, container, false);
        assert getArguments() != null;
        usernameAdmin = getArguments().getString("username"); // username = admin

        Button addEmployButton = view.findViewById(R.id.addEmployButton);
        addEmployButton.setOnClickListener(v -> {
            startActivity(new Intent(this.getActivity(), Create_Employee_Account.class));
        });

        //xử lý nút xóa tài khoản
        Button deleteAccountButton = view.findViewById(R.id.deleteAccountButton);
        deleteAccountButton.setOnClickListener(v -> {
            startActivity(new Intent(this.getActivity(), Delete_Account.class));
        });


        //xử lý nút thay đổi bảo mật
        Button changeSecurityButton = view.findViewById(R.id.changeSecurityButton);
        changeSecurityButton.setOnClickListener(v -> {
            Intent intent = new Intent(this.getActivity(), Change_Security_Admin.class);
            intent.putExtra("username", usernameAdmin);
            startActivity(intent);
        });

        //xử lý nút đăng xuất
        Button logOutButton = view.findViewById(R.id.LogOutButton);
        logOutButton.setOnClickListener(v -> {
            //xử lý nút đăng xuất
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
}