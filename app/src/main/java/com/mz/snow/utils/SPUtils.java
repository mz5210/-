package com.mz.snow.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class SPUtils {

    /**
     * 保存谁登录过
     */
    public static void setUserType(Context context, int status){
        pref(context).edit().putInt(Constance.SP_KEY_USER_TYPE, status).commit();
    }

    /**
     * 获取谁登录过
     */
    public static int getUserType(Context context){
        return pref(context).getInt(Constance.SP_KEY_USER_TYPE, 0);
    }



    /**
     * 保存会话标识
     */
    public static void setToken(Context context, String token) {
        pref(context).edit().putString(Constance.SP_KEY_TOKEN, token).commit();
    }

    /**
     * 获取会话标识
     */
    public static String getToken(Context context) {
        return pref(context).getString(Constance.SP_KEY_TOKEN, "");
    }

    /**
     * 保存用户名
     */
    public static void setUserName(Context context, String username) {
        pref(context).edit().putString(Constance.SP_KEY_USER_NAME, username).commit();
    }

    /**
     * 获取用户名
     */
    public static String getUserName(Context context) {
        return pref(context).getString(Constance.SP_KEY_USER_NAME, "");
    }
    /**
     * 清除账户 密码
     */
    public static void deleteUserAndPassword(Context context) {
        pref(context).edit().putString(Constance.SP_KEY_PASSWORD, "").commit();
        pref(context).edit().putString(Constance.SP_KEY_USER_NAME, "").commit();
    }
    /**
     * 保存登录密码
     */
    public static void setPassword(Context context, String password) {
        pref(context).edit().putString(Constance.SP_KEY_PASSWORD, password).commit();
    }

    /**
     * 获取登录密码
     */
    public static String getPassword(Context context) {
        return pref(context).getString(Constance.SP_KEY_PASSWORD, "");
    }

    private static SharedPreferences pref(Context con) {
        return PreferenceManager.getDefaultSharedPreferences(con);
    }
    /**
     * 保存登录密码
     */
    public static void setUser(Context context, String user) {
        SharedPreferences bmob = context.getSharedPreferences("bmob_sp", 0);
        bmob.edit().putString("user", user).commit();
    }
}
