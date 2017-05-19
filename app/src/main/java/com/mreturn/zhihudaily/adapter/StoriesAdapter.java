package com.mreturn.zhihudaily.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mreturn.zhihudaily.model.StoryListBean;

import java.util.List;

/**
 * Created by mReturn
 * on 2017/5/19.
 */

public class StoriesAdapter extends BaseRecycleViewAdapter<StoryListBean.StoriesBean> {

    private Fragment mFragment;

    public StoriesAdapter(List<StoryListBean.StoriesBean> datas, Fragment mFragment) {
        super(datas);
        this.mFragment = mFragment;
    }

    @Override
    protected int getItemType() {
        return 0;
    }

    @Override
    protected int getItemLayout() {
        return 0;
    }

    @Override
    protected RecyclerView.ViewHolder getItemViewHolder(View itemView) {
        return null;
    }

    @Override
    protected int getTitlLayout() {
        return 0;
    }

    @Override
    protected RecyclerView.ViewHolder getTitleViewHolder(View itemView) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public void addTitle(String title){
        datas.add(new StoryListBean.StoriesBean(title, StoryListBean.StoriesBean.TYPE_TITLE));
        notifyDataSetChanged();

    }
}
