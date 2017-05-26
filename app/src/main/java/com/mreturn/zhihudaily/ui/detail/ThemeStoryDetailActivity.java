package com.mreturn.zhihudaily.ui.detail;

import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.model.StoryDetailBean;

/**
 * Created by mReturn
 * on 2017/5/26.
 */

public class ThemeStoryDetailActivity extends BaseDetailActiivty {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_theme_story_detail;
    }

    @Override
    public void showDetail(StoryDetailBean storyDetail) {
        this.storyDetail = storyDetail;
        loadHtml(storyDetail);
    }
}
