package com.mreturn.zhihudaily.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mreturn.zhihudaily.MainActivity;
import com.mreturn.zhihudaily.Presenter.SplashPresenter;
import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.model.SplashData;
import com.mreturn.zhihudaily.ui.view.SplashView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mReturn
 * on 2017/5/17.
 */

public class SplashActivity extends AppCompatActivity implements SplashView {
    @BindView(R.id.iv_background)
    ImageView ivBackground;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    SplashPresenter mPresenter = new SplashPresenter(this);
    AlphaAnimation animation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


        animation = new AlphaAnimation(0.1f, 1.0f);
        animation.setFillAfter(true);
        animation.setDuration(3000);

        mPresenter.showImg(this);
    }


    @Override
    public void showImg(int resId) {
        ivBackground.setImageResource(resId);
        ivIcon.startAnimation(animation);
        animation.setAnimationListener(new AnimationImpl());

    }

    @Override
    public void showImg(String imgUrl) {
        Glide.with(this)
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivBackground);
        ivIcon.startAnimation(animation);
        animation.setAnimationListener(new AnimationImpl());
    }

    @Override
    public void getSplashDatasuccess(SplashData splashData) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null) mPresenter.dispose();
    }

    private class AnimationImpl implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }
}
