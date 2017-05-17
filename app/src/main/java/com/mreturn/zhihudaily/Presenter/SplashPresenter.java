package com.mreturn.zhihudaily.Presenter;

import android.content.Context;
import android.text.TextUtils;

import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.api.AppClient;
import com.mreturn.zhihudaily.app.Constant;
import com.mreturn.zhihudaily.model.SplashData;
import com.mreturn.zhihudaily.ui.view.SplashView;
import com.mreturn.zhihudaily.utils.NetUtils;
import com.mreturn.zhihudaily.utils.SpUtils;
import com.mreturn.zhihudaily.utils.TransformUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by mReturn
 * on 2017/5/17.
 */

public class SplashPresenter extends BasePresenter {
    private SplashView splashView;

    public SplashPresenter(SplashView splashView) {
        this.splashView = splashView;
    }

    private void loadImg() {
        AppClient.getZhihuApi().getSplashData().compose(
                TransformUtils.<SplashData>defaultSchedulers()).subscribe(new Observer<SplashData>() {
            @Override
            public void onSubscribe(Disposable d) {
                addDisposable(d);
            }

            @Override
            public void onNext(SplashData value) {
                splashView.getSplashDatasuccess(value);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void showImg(Context context) {
        if (NetUtils.isNetAvailable(context)) {
            boolean isAppOpened = (boolean) SpUtils.get(context, Constant.IS_APP_OPENED, false);
            if (!isAppOpened) {
                //第一次打开
                splashView.showImg(R.drawable.splash);
                SpUtils.put(context, Constant.IS_APP_OPENED, true);
                loadImg();
            } else {
                String imgPath = (String) SpUtils.get(context, Constant.SPLASH_IMG_PATH, "");
                if (!TextUtils.isEmpty(imgPath)) {
                    splashView.showImg(imgPath);
                    loadImg();
                } else {
                    splashView.showImg(R.drawable.splash);
                    loadImg();
                }
            }
        } else {
            splashView.showImg(R.drawable.splash);
        }
    }

}
