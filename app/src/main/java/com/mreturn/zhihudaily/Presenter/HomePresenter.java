package com.mreturn.zhihudaily.Presenter;

import android.content.Context;

import com.mreturn.zhihudaily.api.ZhihuClient;
import com.mreturn.zhihudaily.model.StoryListBean;
import com.mreturn.zhihudaily.ui.main.HomeView;

import io.reactivex.functions.Consumer;

/**
 * Created by mReturn
 * on 2017/5/19.
 */

public class HomePresenter extends BasePresenter {
    HomeView homeView;

    public HomePresenter(HomeView homeView) {
        this.homeView = homeView;
    }

    public void loadLatest(Context context){
        ZhihuClient.getZhihuApi().getLatest().doOnNext(new Consumer<StoryListBean>() {
            @Override
            public void accept(StoryListBean storyListBean) throws Exception {

            }
        });
    }

    public void loadMore(String date){

    }

    public void getCache(Context context){

    }

}
