package com.example.laporwargaapp.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "laporan")
public class Laporan implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String kategori;
    public String jalan;
    public String keterangan;

    public double latitude;
    public double longitude;

    public String fotoUri;
    public String tanggal;
    public String status;

    public Laporan() {}

    protected Laporan(Parcel in) {
        id = in.readInt();
        kategori = in.readString();
        jalan = in.readString();
        keterangan = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        fotoUri = in.readString();
        tanggal = in.readString();
        status = in.readString();
    }

    public static final Creator<Laporan> CREATOR = new Creator<Laporan>() {
        @Override
        public Laporan createFromParcel(Parcel in) {
            return new Laporan(in);
        }

        @Override
        public Laporan[] newArray(int size) {
            return new Laporan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(kategori);
        parcel.writeString(jalan);
        parcel.writeString(keterangan);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(fotoUri);
        parcel.writeString(tanggal);
        parcel.writeString(status);
    }
}
