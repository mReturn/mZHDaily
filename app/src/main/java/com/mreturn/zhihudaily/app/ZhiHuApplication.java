package com.mreturn.zhihudaily.app;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.mreturn.zhihudaily.utils.SpUtils;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by mReturn
 * on 2017/5/16.
 */

public class ZhiHuApplication extends Application {
    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();

        //夜间模式
        boolean isNight = (boolean) SpUtils.get(this,Constant.KEY_NIGHT,false);
        if (isNight){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }


        if (LeakCanary.isInAnalyzerProcess(this)) return;
        LeakCanary.install(this);
    }

    public static Context getAppContext() {
        return appContext;
    }
}
