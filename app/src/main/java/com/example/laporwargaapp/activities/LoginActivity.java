package com.example.laporwargaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.example.laporwargaapp.R;
import com.example.laporwargaapp.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {
    EditText edtUser;
    CheckBox cbRemember;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_login);
        SessionManager session = new SessionManager(this);

        if(session.isRememberMe() && session.getUser() != null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        edtUser = findViewById(R.id.edtUser);
        btnLogin = findViewById(R.id.btnLogin);
        cbRemember = findViewById(R.id.cbRemember);

        btnLogin.setOnClickListener(v -> {
            String nama = edtUser.getText().toString().trim();

            if (nama.isEmpty()) {
                edtUser.setError("Nama Lengkap / NIM wajib diisi");
                return;
            }

            if (cbRemember.isChecked()) {
                session.saveUser(nama, true);
            }

            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}