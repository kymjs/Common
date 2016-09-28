package com.kymjs.common;

import android.util.Log;

/**
 * Created by ZhangTao on 6/29/16.
 */
public class Logger {
    public static boolean DEBUG = true;

    public static void log(String msg) {
        if (DEBUG) {
            Log.d("kymjs", "=======" + msg);
        }
    }
}
