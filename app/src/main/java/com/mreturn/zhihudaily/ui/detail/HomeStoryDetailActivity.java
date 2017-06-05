package com.mreturn.zhihudaily.ui.detail;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.model.StoryDetailBean;
import com.mreturn.zhihudaily.utils.ImageLoader;

import butterknife.BindView;

public class HomeStoryDetailActivity extends BaseDetailActiivty {

    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.mask_view)
    View maskView;
    @BindView(R.id.tv_source)
    TextView tvSource;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ctb_layout)
    CollapsingToolbarLayout ctbLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_story_detail;
    }


    @Override
    public void showDetail(StoryDetailBean storyDetail) {
        this.storyDetail = storyDetail;
        ctbLayout.setTitle("");
        tvTitle.setText(storyDetail.getTitle());
        tvSource.setText(storyDetail.getImage_source());
        ImageLoader.display(this, ivTop, storyDetail.getImage());

        loadHtml(storyDetail);
    }


}
