package com.mreturn.zhihudaily.app;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by mReturn
 * on 2017/5/16.
 */

public class BaseApplication extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();


        if (LeakCanary.isInAnalyzerProcess(this)) return;
        LeakCanary.install(this);
    }

    public static Context getAppContext() {
        return appContext;
    }
}
