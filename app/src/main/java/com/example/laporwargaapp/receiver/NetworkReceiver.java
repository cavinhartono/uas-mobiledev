package com.example.laporwargaapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.widget.Toast;

public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(
            Context context,
            Intent intent) {

        ConnectivityManager cm =
                (ConnectivityManager)
                        context.getSystemService(
                                Context.CONNECTIVITY_SERVICE);

        Network network =
                cm.getActiveNetwork();

        if(network == null){

            Toast.makeText(
                            context,
                            "Jaringan Terputus!",
                            Toast.LENGTH_LONG)
                    .show();

            return;
        }

        NetworkCapabilities capabilities =
                cm.getNetworkCapabilities(
                        network);

        if(capabilities == null){

            Toast.makeText(
                            context,
                            "Jaringan Terputus!",
                            Toast.LENGTH_LONG)
                    .show();
        }
    }
}
