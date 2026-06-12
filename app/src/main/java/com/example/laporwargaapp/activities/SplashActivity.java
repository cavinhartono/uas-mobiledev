package com.example.laporwargaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.example.laporwargaapp.R;
import com.example.laporwargaapp.utils.SessionManager;

public class SplashActivity
        extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());

        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            SessionManager session = new SessionManager(this);
            if (session.getUser() != null) {
                startActivity(new Intent(this, MainActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }

            finish();

        },3000);
    }
}