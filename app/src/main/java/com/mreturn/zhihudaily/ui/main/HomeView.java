package com.mreturn.zhihudaily.ui.main;

import com.mreturn.zhihudaily.model.StoriesBean;
import com.mreturn.zhihudaily.model.StoryListBean;

import java.util.List;

/**
 * Created by mReturn
 * on 2017/5/19.
 */

public interface HomeView {
    void setRefreshing(boolean b);

    void setCurrentDate(String date);

    void showStories(StoryListBean storyList);

    void showMoreStory(List<StoriesBean> stories);

    void showLoadLatestError();

    void showLoadMoreError();

    void setLoadMoreViewShow(boolean show);

    void showNoMoreData();


}
