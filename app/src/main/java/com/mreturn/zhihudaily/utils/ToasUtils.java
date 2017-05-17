package com.mreturn.zhihudaily.utils;

import android.os.Handler;
import android.widget.Toast;

import com.mreturn.zhihudaily.app.ZhiHuApplication;

/**
 * Created by mReturn
 * on 2017/5/17.
 * Toast工具类
 */

public class ToasUtils {
    private static Toast toast = null;
    private static Handler mHandler = new Handler(ZhiHuApplication.appContext.getMainLooper());
//    public static void show(final String txt){
//        if (TextUtils.isEmpty(txt)) return;
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (toast == null){
//                    toast = Toast.makeText(ZhiHuApplication.appContext,txt,Toast.LENGTH_SHORT);
//                }else{
//
//                }
//            }
//        });
//    }
}
