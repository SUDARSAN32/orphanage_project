package com.trialapplication.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

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
                case R.id.home:
                     openFragment(new home_fragment());
                    break;
                case R.id.create:
                     openFragment(new create_post());
                    break;
                case R.id.posts:
                     openFragment(new yourpost());
                    break;
            }
            return true;
        });

        fragmentManager = getSupportFragmentManager();
        openFragment(new home_fragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent=new Intent(MainActivity4.this,MainActivity.class);
                startActivity(intent);
                // Start MainActivity or the appropriate activity
                break;
            case R.id.settings:
                Intent intent1=new Intent(MainActivity4.this,settings.class);
                startActivity(intent1);
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
}
