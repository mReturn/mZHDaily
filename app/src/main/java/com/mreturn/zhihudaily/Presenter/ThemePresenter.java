package com.mreturn.zhihudaily.Presenter;

import com.mreturn.zhihudaily.api.ZhihuClient;
import com.mreturn.zhihudaily.app.ZhiHuApplication;
import com.mreturn.zhihudaily.database.ReadDao;
import com.mreturn.zhihudaily.model.StoriesBean;
import com.mreturn.zhihudaily.model.ThemeBean;
import com.mreturn.zhihudaily.ui.theme.ThemeView;
import com.mreturn.zhihudaily.utils.MyLog;
import com.mreturn.zhihudaily.utils.TransformUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by mReturn
 * on 2017/5/23.
 */

public class ThemePresenter extends BaseStoryPresenter {
    ThemeView themeView;

    public ThemePresenter(ThemeView themeView) {
        this.themeView = themeView;
    }

    @SuppressWarnings("unchecked")
    public void getTheme(int themeId) {
        themeView.setRefreshing(true);
        ZhihuClient.getZhihuApi().getTheme(themeId)
                .doOnNext(mConsumer)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new Observer<ThemeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(ThemeBean theme) {
                        themeView.setRefreshing(false);
                        if (theme != null) {
                            themeView.showTheme(theme);
                            List<StoriesBean> stories = theme.getStories();
                            if (stories != null && stories.size()>0){
                                themeView.setLastItemId(stories.get(stories.size() -1).getId());
                            }
                        } else {
                            themeView.showGetThemeError();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        MyLog.e("theme: ",e.toString());
                        themeView.setRefreshing(false);
                        themeView.showGetThemeError();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @SuppressWarnings("unchecked")
    public void getMoreThemeStories(int themeId, int storyId){
        themeView.setLoadMoreViewShow(true);
        ZhihuClient.getZhihuApi().getMoreTheme(themeId,storyId)
                .doOnNext(mConsumer)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new Observer<ThemeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ThemeBean themeBean) {
                        themeView.setLoadMoreViewShow(false);
                        if (themeBean != null && themeBean.getStories() != null
                                && themeBean.getStories().size() >0){
                            List<StoriesBean> stories = themeBean.getStories();
                            themeView.setLastItemId(stories.get(stories.size() -1).getId());
                            themeView.showMoreThemeStory(stories);
                        }else{
                            themeView.showNoMoreData();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        themeView.setLoadMoreViewShow(false);
                        themeView.showLoadMoreError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    protected Consumer mConsumer = new Consumer<ThemeBean>() {
        @Override
        public void accept(ThemeBean themeBean) throws Exception {
            //判断是否已读
            ReadDao readDao = new ReadDao(ZhiHuApplication.getAppContext());
            List<Integer> readList = readDao.getReadList();
            List<StoriesBean> stories = themeBean.getStories();
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
