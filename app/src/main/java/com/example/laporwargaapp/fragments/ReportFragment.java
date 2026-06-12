package com.example.laporwargaapp.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.laporwargaapp.database.AppDatabase;
import com.example.laporwargaapp.database.Laporan;
import com.example.laporwargaapp.service.UploadService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.example.laporwargaapp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReportFragment extends Fragment {

    Spinner spKategori;
    TextView tvLokasi;
    EditText edtJalan;
    EditText edtKeterangan;

    Button btnFoto;
    Button btnKirim;

    ImageView imgFoto;

    Uri photoUri;

    double latitude;
    double longitude;

    private static final int CAMERA_REQUEST = 200;

    AppDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report, container, false);

        spKategori = view.findViewById(R.id.spKategori);
        edtJalan = view.findViewById(R.id.edtJalan);
        tvLokasi = view.findViewById(R.id.tvLokasi);
        edtKeterangan = view.findViewById(R.id.edtKeterangan);
        btnFoto = view.findViewById(R.id.btnFoto);
        btnKirim = view.findViewById(R.id.btnKirim);
        imgFoto = view.findViewById(R.id.imgFoto);

        tvLokasi.setText(latitude + ", " + longitude);

        db = AppDatabase.getInstance(requireContext());

        initSpinner();

        getCurrentLocation();

        btnFoto.setOnClickListener(v -> {
            boolean granted = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

            if(!granted){
                requestPermissions(new String[] {
                        Manifest.permission.CAMERA
                }, CAMERA_REQUEST);
            } else {
                openCamera();
            }
        });
        btnKirim.setOnClickListener(v -> saveLaporan());

        return view;
    }

    private void initSpinner(){

        String[] kategori = {
                "Jalan Berlubang",
                "Lampu Jalan Mati",
                "Tumpukan Sampah"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        kategori);

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spKategori.setAdapter(adapter);
    }

    private void getCurrentLocation(){
        FusedLocationProviderClient client =
                LocationServices
                        .getFusedLocationProviderClient(
                                requireActivity());

        if(ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },1);

            return;
        }

        client.getLastLocation().addOnSuccessListener(location -> {
            if (location != null){
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                tvLokasi.setText(latitude + ", " + longitude);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CAMERA_REQUEST) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else{
                Toast.makeText(requireContext(), "Izin kamera ditolak", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile;

        try {
            photoFile = createImageFile();
        } catch(Exception e){
            e.printStackTrace();
            return;
        }

        photoUri = FileProvider.getUriForFile(
                        requireContext(),
                        requireContext()
                                .getPackageName()
                                + ".provider",
                        photoFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    private File createImageFile() throws IOException {
        String fileName = "LAPORAN_" + System.currentTimeMillis();
        File storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                fileName,
                ".jpg",
                storageDir);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            imgFoto.setImageURI(photoUri);
        }
    }

    private void saveLaporan(){
        if(photoUri == null){
            Toast.makeText(getContext(), "Foto wajib diambil", Toast.LENGTH_SHORT).show();
            return;
        }

        Laporan laporan = new Laporan();
        laporan.kategori = spKategori.getSelectedItem().toString();
        laporan.jalan = edtJalan.getText().toString();
        laporan.keterangan = edtKeterangan.getText().toString();
        laporan.latitude = latitude;
        laporan.longitude = longitude;
        laporan.fotoUri = photoUri.toString();
        laporan.tanggal = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());
        laporan.status = "Terkirim";

        db.laporanDao().insert(laporan);
        requireActivity().getSupportFragmentManager().setFragmentResult("refresh_map", new Bundle());

        Intent intent = new Intent(getContext(), UploadService.class);
        requireActivity().startService(intent);
        Toast.makeText(getContext(), "Laporan Disimpan", Toast.LENGTH_SHORT).show();
        clearForm();
    }

    private void clearForm(){
        edtJalan.setText("");
        edtKeterangan.setText("");
        imgFoto.setImageDrawable(null);
        photoUri = null;
    }

}
