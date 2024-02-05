package com.trialapplication.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import com.trialapplication.myapplication.databinding.ActivityMain4Binding;

public class MainActivity4 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private ActivityMain4Binding binding;
    private LinearLayout postContainer;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.lay1, binding.toolbar, R.string.nav_open, R.string.nav_close
        );
        binding.lay1.addDrawerListener(toggle);
        toggle.syncState();

        binding.menu1.setNavigationItemSelectedListener(this);

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.create:
                    createPostFragment();
                    break;
            }
            return true;
        });

        fragmentManager = getSupportFragmentManager();
        postContainer = findViewById(R.id.post_container);
        openFragment(new post_fragment());


        Bundle args = getIntent().getExtras();
        if (args != null) {
            email = args.getString("email");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(MainActivity4.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.settings:
                break;
            case R.id.viewpost:
                Bundle bundle = new Bundle();
                bundle.putString("email", email);

                Intent intent2 = new Intent(MainActivity4.this, settings.class);
                intent2.putExtras(bundle);
                startActivity(intent2);
                break;
        }
        binding.lay1.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (binding.lay1.isDrawerOpen(GravityCompat.START)) {
            binding.lay1.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame1, fragment);
        fragmentTransaction.commit();
    }

    private void createPostFragment() {
        View postView = LayoutInflater.from(this).inflate(R.layout.fragment_post_fragment, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, 16);

        postView.setLayoutParams(layoutParams);
        postContainer.addView(postView);
    }
}
