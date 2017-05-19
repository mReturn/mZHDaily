package com.mreturn.zhihudaily.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by mReturn
 * on 2017/5/19.
 */

public abstract class LoadMoreScrollListener extends RecyclerView.OnScrollListener {

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int lastVisiableItemPos = layoutManager.findLastVisibleItemPosition();
        if (newState == RecyclerView.SCROLL_STATE_IDLE && recyclerView.getAdapter().getItemCount()>1
                && lastVisiableItemPos + 1 == recyclerView.getAdapter().getItemCount()){
            onLoadMore();
        }
    }

    protected abstract void onLoadMore();
}
