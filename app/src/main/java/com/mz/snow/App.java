package com.mz.snow;

import android.app.Application;

import com.mz.snow.utils.Constance;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2017/12/6.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, Constance.BMOB_APP_ID);
    }
}
