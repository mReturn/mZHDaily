package com.mreturn.zhihudaily.Presenter;

import android.content.Context;

import com.mreturn.zhihudaily.api.ZhihuClient;
import com.mreturn.zhihudaily.app.ZhiHuApplication;
import com.mreturn.zhihudaily.db.ReadDao;
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

public class HomePresenter extends BasePresenter {
    HomeView homeView;

    public HomePresenter(HomeView homeView) {
        this.homeView = homeView;
    }


    @SuppressWarnings("unchecked")
    public void loadLatest(final Context context) {
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
                        if (storyList != null && storyList.getStories() != null && storyList.getStories().size() > 0) {
                            homeView.setCurrentDate(storyList.getDate());
                            homeView.setLoadMoreViewShow(false);
                            homeView.showMoreStory(storyList.getStories());
                        }else{

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

//    public void getCache(Context context) {
//
//    }

    Consumer mConsumer = new Consumer<StoryListBean>() {
        @Override
        public void accept(StoryListBean storyListBean) throws Exception {
            //判断是否已读
            ReadDao readDao = new ReadDao(ZhiHuApplication.getAppContext());
            List<Integer> readList = readDao.getReadList();
            List<StoryListBean.StoriesBean> stories = storyListBean.getStories();
            for (StoryListBean.StoriesBean story : stories) {
                story.setRead(readList.contains(story.getId()));
                if (story.getImages() == null || story.getImages().size() == 0) {
                    story.setShowType(story.TYPE_NO_IMG_STORY);
                }
            }
        }
    };

//    private Observable getLatestObservable(final Context context) {
//        return ZhihuClient.getZhihuApi().getLatest().doOnNext(new Consumer<StoryListBean>() {
//            @Override
//            public void accept(StoryListBean storyListBean) throws Exception {
//                //保存第一页数据
//                StoryDao storyDao = new StoryDao(context);
//                storyDao.saveStoryList(storyListBean.getStories(), storyListBean.getTop_stories());
//                SpUtils.put(context, Constant.KEY_CURRENT_DATE, storyListBean.getDate());
//                SpUtils.put(context, Constant.KEY_HAS_CACHE, true);
//            }
//        }).doOnNext(mConsumer);
//    }

}
