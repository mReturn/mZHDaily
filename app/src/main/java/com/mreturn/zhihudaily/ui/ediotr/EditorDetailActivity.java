package com.mreturn.zhihudaily.ui.ediotr;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mreturn.zhihudaily.Presenter.BasePresenter;
import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.app.Constant;
import com.mreturn.zhihudaily.ui.BaseToolBarAtivity;
import com.mreturn.zhihudaily.utils.CommonUtils;
import com.mreturn.zhihudaily.utils.MyLog;
import com.mreturn.zhihudaily.utils.ToastShow;

import butterknife.BindView;

/**
 * Created by mReturn
 * on 2017/5/24.
 */

public class EditorDetailActivity extends BaseToolBarAtivity {
    @BindView(R.id.wv_editor_detail)
    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editor_detail;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        initBackToolBar(R.string.editor_info_title);
        CommonUtils.initWebView(this,webView);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                MyLog.e("editor0: ",view.getUrl());
                return super.shouldOverrideUrlLoading(view, request);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                MyLog.e("editor1: ",url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String id = getIntent().getStringExtra(Constant.EDITOR_ID);
        ToastShow.show(id);
        String url = String.format(Constant.EDITOR_DETAIL_URL,id);
        webView.loadUrl(url);
    }
}
