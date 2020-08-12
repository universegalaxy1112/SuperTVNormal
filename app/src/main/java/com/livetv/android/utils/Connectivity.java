package com.livetv.android.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.livetv.android.LiveTvApplication;

public class Connectivity {
    public static boolean isConnected() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) LiveTvApplication.getAppContext().getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
