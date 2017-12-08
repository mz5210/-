package com.mz.snow.utils;

import android.util.Log;

import com.mz.snow.BuildConfig;


/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class LogX {

    public LogX() {
    }

    private static final String RELEASE_MODE = "release";
    private static final String TAG = "YourAppName";

    public static void v(String msg) {
        outputLog(msg);
    }

    public static void e(String msg, Exception e) {
        outputLog(Log.ERROR, msg, e);
    }

    public static void w(String msg) {
        outputLog(Log.WARN, msg);
    }

    public static void outputLog(String msg) {
        outputLog(Log.DEBUG, msg, null);
    }

    public static void outputLog(int type, String msg) {
        outputLog(type, msg, null);
    }

    public static void outputLog(int type, String msg, Exception e) {
        if(!BuildConfig.LOG_MODE) return;

        switch(type) {
            case Log.ASSERT:
                break;
            case Log.ERROR:
                Log.e(TAG, msg, e);
                break;
            case Log.WARN:
                Log.w(TAG, msg);
                break;
            case Log.DEBUG:
                Log.d(TAG, msg);
                break;
            case Log.INFO:
                Log.i(TAG, msg);
                break;
            case Log.VERBOSE:
                Log.v(TAG, msg);
                break;
            default:
                break;
        }
    }

}
