package com.example.laporwargaapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LaporanDao {

    @Insert
    void insert(Laporan laporan);

    @Query("SELECT * FROM laporan ORDER BY id DESC")
    List<Laporan> getAll();

}
