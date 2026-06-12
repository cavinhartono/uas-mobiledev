package com.example.laporwargaapp.activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.example.laporwargaapp.R;
import com.example.laporwargaapp.database.Laporan;

public class DetailActivity extends AppCompatActivity {
    private ImageView imgDetail;
    private TextView tvNamaJalan;
    private TextView tvKeterangan;
    private TextView tvLokasi;
    private TextView tvKoordinat;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_detail);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detail Laporan");
        }

        imgDetail = findViewById(R.id.imgDetail);
        tvNamaJalan = findViewById(R.id.tvNamaJalan);
        tvKeterangan = findViewById(R.id.tvKeterangan);
        tvLokasi = findViewById(R.id.tvLokasi);
        tvKoordinat = findViewById(R.id.tvKoordinat);
        tvStatus = findViewById(R.id.tvStatus);

        Laporan laporan = getIntent().getParcelableExtra("laporan");

        if (laporan != null) {
            tvNamaJalan.setText(laporan.jalan);
            tvKeterangan.setText(laporan.keterangan);
            tvLokasi.setText(laporan.jalan);
            tvKoordinat.setText(laporan.latitude + ", " + laporan.longitude);
            tvStatus.setText("Status : " + laporan.status);
            if(laporan.fotoUri != null){
                imgDetail.setImageURI(Uri.parse(laporan.fotoUri));
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}