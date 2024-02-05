package com.trialapplication.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.trialapplication.myapplication.databinding.ActivityMainOrphanageBinding;

public class MainActivity_orphanage extends AppCompatActivity {

    private ActivityMainOrphanageBinding binding;
    private BottomNavigationView bottomNavigationView;
    private Bundle receivedBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainOrphanageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        receivedBundle = getIntent().getExtras();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.create1:
                    showPostFragments();
                    return true;
                default:
                    return false;
            }
        });
    }

    private void showPostFragments() {
        view_fragment_orphanage fragmentOrphanage = new view_fragment_orphanage();
        Bundle args = new Bundle();
        args.putString("action", "view_posts");

        // Retrieve the email from the previous bundle
        if (receivedBundle != null) {
            String email = receivedBundle.getString("email1");
            args.putString("email", email);
        }

        fragmentOrphanage.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame1, fragmentOrphanage)
                .addToBackStack(null)
                .commit();
    }
}
