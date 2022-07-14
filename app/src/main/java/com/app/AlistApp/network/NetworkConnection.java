package com.app.AlistApp.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;

public class NetworkConnection {
   public static boolean isNwAvailable=true;
    private static final String TAG = "ttt";
    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;

    public NetworkConnection(Context context) {
        connectivityManager =
                (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
    }

    public void registerCallBack(Context context, ConnectivityManager.NetworkCallback callback){
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build();
         networkCallback =callback;
        connectivityManager.requestNetwork(networkRequest, networkCallback);

    }
    public void filterTransaction(){

    }

    @SuppressLint("MissingPermission")
    public boolean isNetworkAvailable(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (connectivityManager.getActiveNetwork()==null){
                return false;
            }
        }else {
            return connectivityManager.getActiveNetworkInfo().isConnected();

        }
        return true;
    }

    public void unRegisterCallBack(){

      connectivityManager.unregisterNetworkCallback(networkCallback);
    }


}
