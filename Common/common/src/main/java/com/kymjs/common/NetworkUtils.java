package com.kymjs.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by ZhangTao on 12/25/16.
 */

public class NetworkUtils {
    public static final int NETWORK_INVALID = -1;
    public static final int NETWORK_WAP = 0;
    public static final int NETWORK_2G = 1;
    public static final int NETWORK_3G = 2;
    public static final int NETWORK_4G = 3;
    public static final int NETWORK_WIFI = 10;
    private static final int[] NETWORK_MATCH_TABLE = new int[]{1, 1, 1, 2, 1, 2, 2, 1, 2, 2, 2, 1, 2, 3, 2, 2};

    public NetworkUtils() {
    }

    public static int type(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        int networkType;
        if (!networkConnected(networkInfo)) {
            networkType = -1;
        } else if (isWifiNetwork(networkInfo)) {
            networkType = 10;
        } else if (isWapNetwork(networkInfo)) {
            networkType = 0;
        } else if (isMobileNetwork(networkInfo)) {
            networkType = NETWORK_MATCH_TABLE[telephonyNetworkType(context)];
        } else {
            networkType = 1;
        }

        return networkType;
    }

    private static int telephonyNetworkType(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = telephonyManager.getNetworkType();
        if (networkType < 0 || networkType >= NETWORK_MATCH_TABLE.length) {
            networkType = 0;
        }

        return networkType;
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    public static boolean isNetWorkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return networkConnected(connectivityManager.getActiveNetworkInfo());
    }

    private static boolean networkConnected(NetworkInfo networkInfo) {
        return networkInfo != null && networkInfo.isConnected();
    }

    private static boolean isMobileNetwork(NetworkInfo networkInfo) {
        return networkInfo.getType() == 0;
    }

    private static boolean isWapNetwork(NetworkInfo networkInfo) {
        return isMobileNetwork(networkInfo) && !TextUtils.isEmpty(Proxy.getDefaultHost());
    }

    public static boolean isWifiNetwork(NetworkInfo networkInfo) {
        return networkInfo.getType() == 1;
    }
}
