package com.mreturn.zhihudaily.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by mReturn
 * on 2017/5/16.
 * 网络工具类
 */

public class NetUtils {

    /**
     * 网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager){
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (null != info && info.isConnected()){
                if (info.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 是否是wifi连接
     * @param context
     * @return
     */
    public static boolean isWifi(Context context){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager == null && connectivityManager.
                getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 打开网络设置页面
     * @param activity
     */
    public static void openNetSetting(Activity activity){
        Intent intent = new Intent("/");
        ComponentName componentName = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(componentName);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivity(intent);

    }
}
