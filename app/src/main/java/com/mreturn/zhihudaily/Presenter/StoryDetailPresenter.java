package com.mreturn.zhihudaily.Presenter;

import android.content.Context;

import com.mreturn.zhihudaily.api.ZhihuClient;
import com.mreturn.zhihudaily.database.CollectDao;
import com.mreturn.zhihudaily.database.PraiseDao;
import com.mreturn.zhihudaily.model.StoriesBean;
import com.mreturn.zhihudaily.model.StoryDetailBean;
import com.mreturn.zhihudaily.model.StoryExtraBean;
import com.mreturn.zhihudaily.ui.story.DetailView;
import com.mreturn.zhihudaily.utils.TransformUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by mReturn
 * on 2017/5/24.
 */

public class StoryDetailPresenter extends BasePresenter {

    DetailView detailView;

    public StoryDetailPresenter(DetailView detailView) {
        this.detailView = detailView;
    }

    public void getStoryDetail(int storyId){
        detailView.showLoadingView(true);
        ZhihuClient.getZhihuApi().getStoryDetail(storyId)
                .compose(TransformUtils.<StoryDetailBean>defaultSchedulers())
                .subscribe(new Observer<StoryDetailBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(StoryDetailBean storyDetail) {
                        detailView.showLoadingView(false);
                        detailView.showDetail(storyDetail);
                    }

                    @Override
                    public void onError(Throwable e) {
                        detailView.showLoadingView(false);
                        detailView.showLoadFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getStoryExtra(int storyId){
        ZhihuClient.getZhihuApi().getStoryExtra(storyId)
                .compose(TransformUtils.<StoryExtraBean>defaultSchedulers())
                .subscribe(new Observer<StoryExtraBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(StoryExtraBean storyExtra) {
                        detailView.showExtra(storyExtra);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    //-------------------------------collect---------------------------------------
    public void collectStory(Context context, StoriesBean story) {
        CollectDao collectDao = new CollectDao(context);
        boolean success = collectDao.save(story);
        if (success) {
            detailView.setCollectState(true);
        } else {
            detailView.showCollectFail();
        }
    }

    public void removeCollect(Context context, StoriesBean story) {
        CollectDao collectDao = new CollectDao(context);
        boolean success = collectDao.delete(story);
        if (success) {
            detailView.setCollectState(false);
        } else {
            detailView.showUnCollectFail();
        }

    }

    public List<StoriesBean> getCollectList(Context context) {
        CollectDao collectDao = new CollectDao(context);
        return collectDao.getCollectList();
    }

    public List<Integer> getCollectIdList(Context context){
        CollectDao collectDao =  new CollectDao(context);
        return collectDao.getCollectIdList();
    }

    //------------------------------------praise----------------------------------
    public void savePraise(Context context,int storyId){
        PraiseDao praiseDao = new PraiseDao(context);
        praiseDao.save(storyId);
    }

    public void removePraise(Context context,int storyID){
        PraiseDao praiseDao = new PraiseDao(context);
        praiseDao.delete(storyID);
    }

    public List<Integer> getPraiseIdList(Context context){
        PraiseDao praiseDao = new PraiseDao(context);
        return praiseDao.getPraisetIdLis();
    }


}
