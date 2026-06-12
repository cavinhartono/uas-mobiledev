package com.example.laporwargaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laporwargaapp.R;
import com.example.laporwargaapp.activities.DetailActivity;
import com.example.laporwargaapp.database.Laporan;

import java.util.List;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.ViewHolder>{
    Context context;
    List<Laporan> list;

    public LaporanAdapter(Context context, List<Laporan> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_laporan, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Laporan laporan = list.get(position);
        holder.tvKategori.setText(laporan.kategori);
        holder.tvJalan.setText(laporan.jalan);
        holder.tvTanggal.setText(laporan.tanggal);
        holder.tvKoordinat.setText(laporan.latitude + ", " + laporan.longitude);
        holder.tvStatus.setText(laporan.status);

        if (laporan.fotoUri != null) {
            holder.imgItem.setImageURI(Uri.parse(laporan.fotoUri));
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("laporan",laporan);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView tvKategori,
                tvJalan,
                tvTanggal,
                tvKoordinat,
                tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            tvKategori = itemView.findViewById(R.id.tvKategori);
            tvJalan = itemView.findViewById(R.id.tvJalan);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvKoordinat = itemView.findViewById(R.id.tvKoordinat);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
