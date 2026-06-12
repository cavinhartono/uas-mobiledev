package com.example.laporwargaapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laporwargaapp.R;
import com.example.laporwargaapp.activities.DetailActivity;
import com.example.laporwargaapp.adapter.LaporanAdapter;
import com.example.laporwargaapp.database.AppDatabase;
import com.example.laporwargaapp.database.Laporan;

import java.util.List;

public class HistoryFragment extends Fragment {
    RecyclerView recyclerView;
    AppDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = view.findViewById(R.id.recyclerHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        db = AppDatabase.getInstance(getContext());
        loadData();
        return view;
    }

    private void loadData() {
        List<Laporan> list = db.laporanDao().getAll();
        LaporanAdapter adapter = new LaporanAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
    }
}
