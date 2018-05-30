package com.willme.topactivity;

/**
 * Created by djf on 2018/5/18.
 */

public class Log {
    private static final String appTAG = "TopActivity";
    private static boolean isLog = true;

    public static void d(String TAG, String msg) {
        if (isLog) {
            android.util.Log.d(appTAG, TAG + ": " + msg);
        }
    }

}
