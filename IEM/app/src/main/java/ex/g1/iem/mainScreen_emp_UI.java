package ex.g1.iem;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fragment.depart_emp_fragment;
import fragment.home_admin_fragment;
import fragment.home_emp_fragment;
import fragment.manage_admin_fragment;
import fragment.project_emp_fragment;
import fragment.setting_admin_fragment;
import fragment.user_emp_fragment;

public class mainScreen_emp_UI extends AppCompatActivity {
    String usernameEmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // ẩn thanh điều hướng
        View decorView = getWindow().getDecorView();
        ((View) decorView).setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_main_screen_emp_ui);
        usernameEmp = getIntent().getStringExtra("username");
        loadFragment(new home_emp_fragment(), usernameEmp);
        BottomNavigationView bottomNavigation_bar = findViewById(R.id.bottom_navigation);
        bottomNavigation_bar.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            if (item.getItemId() == R.id.home) {
                fragment = new home_emp_fragment();
                loadFragment(fragment, usernameEmp);
                return true;
            } else if (item.getItemId() == R.id.depart) {
                fragment = new depart_emp_fragment();
                loadFragment(fragment, usernameEmp);
                return true;
            } else if (item.getItemId() == R.id.project) {
                fragment = new project_emp_fragment();
                loadFragment(fragment, usernameEmp);
                return true;
            }
            else if (item.getItemId() == R.id.profile) {
                fragment = new user_emp_fragment();
                loadFragment(fragment, usernameEmp);
                return true;
            }
            return false;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainScreen_emp), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadFragment(Fragment fragment, String username) {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}