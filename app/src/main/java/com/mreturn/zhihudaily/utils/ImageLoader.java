package com.mreturn.zhihudaily.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.mreturn.zhihudaily.app.ZhiHuApplication;

/**
 * Created by mReturn
 * on 2017/5/19.
 */

public class ImageLoader {

    public static void display(Context context, ImageView iv, String url){
        Glide.with(context)
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);
    }

    public static void displayCircleImg(final ImageView imageView, String imageUrl) {
        displayCircleImg(imageView, imageUrl, -1);
    }

    public static void displayCircleImg(final ImageView imageView, String imageUrl, int resId) {
        Glide.with(ZhiHuApplication.appContext)
                .load(imageUrl)
                .asBitmap()
                .placeholder(resId)
                .error(resId)
                .centerCrop()
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(ZhiHuApplication.appContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

}
