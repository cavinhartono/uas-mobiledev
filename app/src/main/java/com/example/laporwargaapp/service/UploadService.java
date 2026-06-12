package com.example.laporwargaapp.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;

import com.example.laporwargaapp.R;

public class UploadService extends Service {

    public static final String CHANNEL_ID =
            "LAPORAN_CHANNEL";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    @Override
    public int onStartCommand(
            Intent intent,
            int flags,
            int startId) {

        new Thread(() -> {

            try {

                Thread.sleep(3000);

            } catch (Exception e) {
                e.printStackTrace();
            }

            showNotification();

            stopSelf();

        }).start();

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification() {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(
                        this,
                        CHANNEL_ID)

                        .setSmallIcon(
                                R.drawable.ic_launcher_foreground)

                        .setContentTitle(
                                "Sistem Pengaduan")

                        .setContentText(
                                "Laporan Diterima!")

                        .setPriority(
                                NotificationCompat.PRIORITY_HIGH)

                        .setAutoCancel(true);

        NotificationManager manager =
                (NotificationManager)
                        getSystemService(
                                NOTIFICATION_SERVICE);

        manager.notify(100, builder.build());
    }

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            "Laporan Channel",
                            NotificationManager
                                    .IMPORTANCE_HIGH);

            NotificationManager manager =
                    getSystemService(
                            NotificationManager.class);

            manager.createNotificationChannel(
                    channel);
        }
    }
}
