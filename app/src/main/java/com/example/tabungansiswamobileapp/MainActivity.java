package com.example.tabungansiswamobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new MenuFragment());

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;

                switch (item.getItemId()){
                    case R.id.nav_menu:
                        fragment = new MenuFragment();
                        break;
                    case R.id.nav_transaction:
                        fragment = new TransaksiFragment();
                        break;
                    case R.id.nav_detail_transaction:
                        fragment = new DetailTransaksiFragment();
                        break;
                    case R.id.nav_profile:
                        fragment = new ProfilFragment();
                        break;
                    default:
                        fragment = new MenuFragment();
                        break;
                }

                loadFragment(fragment);
                return true;
            }
        });

// Set initial selected item
        bottomNavigationView.setSelectedItemId(R.id.nav_menu);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(MenuFragment.class.getSimpleName())
                .commit();
    }
}