package com.example.laporwargaapp.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.laporwargaapp.R;
import com.example.laporwargaapp.activities.DetailActivity;
import com.example.laporwargaapp.database.AppDatabase;
import com.example.laporwargaapp.database.Laporan;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.List;

public class HomeFragment extends Fragment {
    private MapView mapView;
    private FusedLocationProviderClient locationClient;
    private Button btnReport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Configuration.getInstance().setUserAgentValue(requireContext().getPackageName());
        mapView = view.findViewById(R.id.mapView);
        btnReport = view.findViewById(R.id.menu_report);
        btnReport.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, new ReportFragment())
                    .addToBackStack(null)
                    .commit();
        });
        mapView.setMultiTouchControls(true);
        locationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        getParentFragmentManager().setFragmentResultListener("refresh_map",
                getViewLifecycleOwner(), (requestKey, bundle) -> {
                    mapView.getOverlays().clear();
                    loadCurrentLocation();
                });
        loadCurrentLocation();
        return view;
    }

    private void loadCurrentLocation() {

        if(ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },100);

            return;
        }

        locationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();

                GeoPoint myLocation = new GeoPoint(lat,lng);
                mapView.getController().setZoom(18.0);
                mapView.getController().setCenter(myLocation);

                Marker marker = new Marker(mapView);
                marker.setPosition(myLocation);
                marker.setTitle("Posisi Saya");
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                mapView.getOverlays().add(marker);
                loadLaporanMarkers();
                mapView.invalidate();
            }
        });
    }

    private void loadLaporanMarkers(){
        AppDatabase db = AppDatabase.getInstance(requireContext());
        List<Laporan> laporanList = db.laporanDao().getAll();
        for(Laporan laporan : laporanList){
            GeoPoint point = new GeoPoint(laporan.latitude, laporan.longitude);
            Marker marker = new Marker(mapView);
            marker.setPosition(point);
            marker.setTitle(laporan.kategori);
            marker.setSnippet(laporan.jalan);
            marker.setOnMarkerClickListener((clickedMarker, mapView) -> {
                Intent intent = new Intent(requireContext(), DetailActivity.class);
                intent.putExtra("laporan", laporan);
                startActivity(intent);

                return true;
            });
            this.mapView.getOverlays().add(marker);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
}