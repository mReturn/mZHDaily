package com.mreturn.zhihudaily.ui.collect;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mreturn.zhihudaily.Presenter.BasePresenter;
import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.adapter.HomeStoriesAdapter;
import com.mreturn.zhihudaily.database.CollectDao;
import com.mreturn.zhihudaily.model.StoriesBean;
import com.mreturn.zhihudaily.ui.BaseToolBarAtivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by mReturn
 * on 2017/6/1.
 */

public class CollectActivity extends BaseToolBarAtivity {
    @BindView(R.id.rv_collect)
    RecyclerView rvCollect;

    HomeStoriesAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        initBackToolBar(null);

        rvCollect.setLayoutManager(new LinearLayoutManager(this));
        rvCollect.setHasFixedSize(true);
        mAdapter = new HomeStoriesAdapter(new ArrayList<StoriesBean>(), this);
        rvCollect.setAdapter(mAdapter);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        CollectDao collectDao = new CollectDao(this);
        List<StoriesBean> storyList = collectDao.getCollectList();
        if (storyList != null && storyList.size() > 0) {
            initBackToolBar(storyList.size() + " 条收藏");
            mAdapter.clearData();
            mAdapter.addDatas(storyList);
        }
    }
}
