package com.mreturn.zhihudaily.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;
import com.mreturn.zhihudaily.app.ZhiHuApplication;

/**
 * Created by mReturn
 * on 2017/5/17.
 * Toast工具类
 */

public class ToastShow {
    private static Toast toast = null;
    private static WeakHandler mHandler = new WeakHandler(ZhiHuApplication.appContext.getMainLooper());

    public static void show(final String txt) {
        if (TextUtils.isEmpty(txt))
            return;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(ZhiHuApplication.appContext, txt, Toast.LENGTH_SHORT);
                } else {
                    toast.setText(txt);
                }
                toast.show();
            }
        });
    }

    public static void show(int resId){
        show(ZhiHuApplication.appContext.getString(resId));
    }
}
