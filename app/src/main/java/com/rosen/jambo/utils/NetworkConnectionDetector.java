package com.rosen.jambo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Derick W on 15,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public class NetworkConnectionDetector {
    private Context context;

    public NetworkConnectionDetector(Context context) {
        this.context = context;
    }

    //method to handle the check, returns a boolean as a determinant
    public boolean InternetConnectionStatus() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null) {
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
