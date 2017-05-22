package com.mreturn.zhihudaily.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mReturn
 * on 2017/5/22.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public void setText(int viewId,String text){
        TextView textView = getView(viewId);
        textView.setText(text);
    }

    public void setImageResource(int viewId, int resId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(viewId);
    }

    public void setBgResource(int viewId, int resId) {
        View view = getView(viewId);
        view.setBackgroundResource(resId);
    }

    public void setClickListener(int resId, View.OnClickListener listener) {
        View view = getView(resId);
        view.setOnClickListener(listener);
    }

    public void setSimClickListener(View.OnClickListener listener, int... viewId) {
        for (int id : viewId) {
            View view = getView(id);
            view.setOnClickListener(listener);
        }
    }
}
