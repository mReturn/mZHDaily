package com.mreturn.zhihudaily.ui.detail;

import com.mreturn.zhihudaily.model.StoryDetailBean;
import com.mreturn.zhihudaily.model.StoryExtraBean;

/**
 * Created by mReturn
 * on 2017/5/24.
 */

public interface DetailView {

    void showLoadingView(boolean show);

    void showDetail(StoryDetailBean storyDetail);

    void showExtra(StoryExtraBean storyExtra);

    void showLoadFail();

    void setCollectState(boolean isCollected);

    void showCollectFail();

    void showUnCollectFail();

}
