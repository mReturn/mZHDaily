package com.mreturn.zhihudaily.Presenter;

import android.content.Context;

import com.mreturn.zhihudaily.api.ZhihuClient;
import com.mreturn.zhihudaily.app.ZhiHuApplication;
import com.mreturn.zhihudaily.database.ReadDao;
import com.mreturn.zhihudaily.model.StoriesBean;
import com.mreturn.zhihudaily.model.StoryListBean;
import com.mreturn.zhihudaily.ui.main.HomeView;
import com.mreturn.zhihudaily.utils.MyLog;
import com.mreturn.zhihudaily.utils.TransformUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by mReturn
 * on 2017/5/19.
 */

public class HomePresenter extends BaseStoryPresenter {
    HomeView homeView;

    public HomePresenter(HomeView homeView) {
        this.homeView = homeView;
    }


    @SuppressWarnings("unchecked")
    public void loadLatest(final Context context) {
        homeView.setRefreshing(true);
        ZhihuClient.getZhihuApi().getLatest()
                .doOnNext(mConsumer)
                .compose(TransformUtils.<StoryListBean>defaultSchedulers())
                .subscribe(new Observer<StoryListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(StoryListBean storyList) {
                        homeView.setRefreshing(false);
                        if (storyList != null && storyList.getStories() != null && storyList.getStories().size() > 0) {
                            homeView.showStories(storyList);
                            homeView.setCurrentDate(storyList.getDate());
                        } else {
                            homeView.showLoadLatestError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        homeView.setRefreshing(false);
                        homeView.showLoadLatestError();
                        MyLog.e("latest error: ",e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                })
        ;
    }

    @SuppressWarnings("unchecked")
    public void loadMore(String date) {
        MyLog.e("load more","-------");
        homeView.setLoadMoreViewShow(true);
        ZhihuClient.getZhihuApi().loadMore(date)
                .doOnNext(mConsumer)
                .compose(TransformUtils.<StoryListBean>defaultSchedulers())
                .subscribe(new Observer<StoryListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(StoryListBean storyList) {
                        homeView.setLoadMoreViewShow(false);
                        if (storyList != null && storyList.getStories() != null && storyList.getStories().size() > 0) {
                            homeView.setCurrentDate(storyList.getDate());
                            homeView.showMoreStory(storyList.getStories());
                        }else{
                            homeView.showNoMoreData();
                        }
                    }


                    @Override
                    public void onError(Throwable e) {
                        homeView.showLoadMoreError();
                        homeView.setLoadMoreViewShow(false);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    protected Consumer mConsumer = new Consumer<StoryListBean>() {
        @Override
        public void accept(StoryListBean storyListBean) throws Exception {
            //判断是否已读
            ReadDao readDao = new ReadDao(ZhiHuApplication.getAppContext());
            List<Integer> readList = readDao.getReadList();
            List<StoriesBean> stories = storyListBean.getStories();
            for (StoriesBean story : stories) {
                story.setRead(readList.contains(story.getId()));
                //判断是否有图片
                if (story.getImages() == null || story.getImages().size() == 0) {
                    story.setShowType(story.TYPE_NO_IMG_STORY);
                }
            }
        }
    };

}
