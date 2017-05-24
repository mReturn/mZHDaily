package com.mreturn.zhihudaily.Presenter;

import com.mreturn.zhihudaily.ui.story.DetailView;

/**
 * Created by mReturn
 * on 2017/5/24.
 */

public class StoryDetailPresenter extends BasePresenter {

    DetailView detailView;

    public StoryDetailPresenter(DetailView detailView) {
        this.detailView = detailView;
    }
}
