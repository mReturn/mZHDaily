package com.mreturn.zhihudaily.ui.splash;

import com.mreturn.zhihudaily.model.SplashData;

/**
 * Created by mReturn
 * on 2017/5/17.
 */

public interface SplashView {

    void showImg(int resId);

    void showImg(String imgUrl);

    void getSplashDatasuccess(SplashData splashData);
}
