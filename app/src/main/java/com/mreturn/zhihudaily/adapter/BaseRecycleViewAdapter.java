package com.mreturn.zhihudaily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mReturn
 * on 2017/5/22.
 */

public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    public List<T> mDatas;
    public Context mContext;
    LayoutInflater mInflater;

    public BaseRecycleViewAdapter(Context context , List<T> datas) {
        this.mDatas = (datas == null) ? new ArrayList<T>() : datas;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = new BaseViewHolder(mInflater.inflate(getItemLayoutId(viewType),parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindData(holder,position, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public abstract int getItemLayoutId(int layoutID);
    public abstract void bindData(BaseViewHolder holder,int position, T item);

}
