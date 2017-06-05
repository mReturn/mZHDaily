package com.mreturn.zhihudaily.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mreturn.zhihudaily.utils.MyLog;

/**
 * Created by mReturn
 * on 2017/5/19.
 */

public abstract class LoadMoreScrollListener extends RecyclerView.OnScrollListener {
    int dy ;
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
       this.dy = dy;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int lastVisiableItemPos = layoutManager.findLastVisibleItemPosition();
        if (newState == RecyclerView.SCROLL_STATE_IDLE && recyclerView.getAdapter().getItemCount()>1
                && lastVisiableItemPos + 1 == recyclerView.getAdapter().getItemCount() && dy>0){
            MyLog.e("scroll: ","dy : "+dy);
            onLoadMore();
        }
    }

    protected abstract void onLoadMore();
}
