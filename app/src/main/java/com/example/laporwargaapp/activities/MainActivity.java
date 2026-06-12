package com.example.laporwargaapp.activities;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;

import com.example.laporwargaapp.R;
import com.example.laporwargaapp.fragments.ProfileFragment;
import com.example.laporwargaapp.fragments.HistoryFragment;
import com.example.laporwargaapp.fragments.HomeFragment;
import com.example.laporwargaapp.fragments.ReportFragment;
import com.example.laporwargaapp.receiver.NetworkReceiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    private NetworkReceiver networkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());

        setContentView(R.layout.activity_main);

        networkReceiver = new NetworkReceiver();
        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        bottomNav = findViewById(R.id.bottomNav);

        loadFragment(new HomeFragment());

        bottomNav.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.menu_home){
                loadFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.menu_history){
                loadFragment(new HistoryFragment());
            } else {
                loadFragment(new ProfileFragment());
            }

            return true;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkReceiver);
    }

    private void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.frameLayout,
                        fragment)
                .commit();
    }
}