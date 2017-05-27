package com.mreturn.zhihudaily.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mreturn.zhihudaily.R;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by mReturn
 * on 2017/5/27.
 */

public class ImgGalleryPageAdapter extends PagerAdapter {

    List<String> imgList;

    public ImgGalleryPageAdapter(List<String> imgList) {
        this.imgList = imgList;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        Glide.with(container.getContext())
                .load(imgList.get(position))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.default_pic_content_image_download_light)
                .dontAnimate()
                .fitCenter()
                .error(R.drawable.default_pic_content_image_download_light)
                .into(photoView);
        photoView.setZoomable(true);
        container.addView(photoView,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
