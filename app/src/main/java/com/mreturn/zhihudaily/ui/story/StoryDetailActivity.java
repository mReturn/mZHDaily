package com.mreturn.zhihudaily.ui.story;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.Menu;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mreturn.zhihudaily.Presenter.BasePresenter;
import com.mreturn.zhihudaily.Presenter.StoryDetailPresenter;
import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.ui.BaseToolBarAtivity;
import com.mreturn.zhihudaily.utils.CommonUtils;

import butterknife.BindView;

public class StoryDetailActivity extends BaseToolBarAtivity implements DetailView{

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
    @BindView(R.id.cl_pb)
    ContentLoadingProgressBar clProgressBar;
    @BindView(R.id.web_story_detail)
    WebView webView;

    StoryDetailPresenter detailPresenter = new StoryDetailPresenter(this);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_story_detaila;
    }

    @Override
    protected BasePresenter createPresenter() {
        return detailPresenter;
    }

    @Override
    protected void initView() {
        initBackToolBar(null);
        CommonUtils.initWebView(this,webView);
        //添加js
        webView.addJavascriptInterface(this,"ZhihuDaily");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @JavascriptInterface
    public void clickToLoadImage(String imaPath){

    }

}
