package com.example.givio;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainPage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;

    private String userName, userEmail, userPhone;
    private static final int ADD_POST_REQUEST = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        floatingActionButton = findViewById(R.id.fab_add_post);

        // Get user info from SignUp
        Intent intent = getIntent();
        if (intent != null) {
            userName = intent.getStringExtra("name");
            userEmail = intent.getStringExtra("email");
            userPhone = intent.getStringExtra("phone");
        }

        // Load default fragment
        loadFragment(new HomeFragment());

        // Bottom navigation click listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (id == R.id.nav_profile) {
                selectedFragment = createUserProfileFragment();
            } else if (id == R.id.nav_info) {
                selectedFragment = new InfoFragment();
            } else if (id == R.id.nav_orglist) {
                selectedFragment = new OrgListFragment();
            }

            if (selectedFragment != null) loadFragment(selectedFragment);
            return true;
        });

        // Floating button click opens AddPost with startActivityForResult
        floatingActionButton.setOnClickListener(v -> {
            Intent addPostIntent = new Intent(MainPage.this, AddPost.class);
            startActivityForResult(addPostIntent, ADD_POST_REQUEST);
        });
    }

    // Load fragment helper
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    // Create UserProfileFragment with user info
    private UserProfileFragment createUserProfileFragment() {
        UserProfileFragment profileFragment = new UserProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", userName);
        bundle.putString("email", userEmail);
        bundle.putString("phone", userPhone);
        profileFragment.setArguments(bundle);
        return profileFragment;
    }

    // Load profile fragment immediately
    private void openUserProfileFragment() {
        loadFragment(createUserProfileFragment());
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
    }

    // Handle AddPost result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_POST_REQUEST && resultCode == RESULT_OK && data != null) {
            boolean openProfile = data.getBooleanExtra("openProfileFragment", false);
            if (openProfile) {
                openUserProfileFragment();
            }
        }
    }
}
