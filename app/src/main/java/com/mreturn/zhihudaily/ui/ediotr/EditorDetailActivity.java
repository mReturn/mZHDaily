package com.mreturn.zhihudaily.ui.ediotr;

import android.content.Intent;
import android.net.Uri;
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

import java.lang.reflect.Method;

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
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String id = getIntent().getStringExtra(Constant.EDITOR_ID);
        ToastShow.show(id);
        final String url = String.format(Constant.EDITOR_DETAIL_URL,id);
        webView.loadUrl(url);
        try {
            Class clsWebSettingsClassic =
                    getClassLoader().loadClass("android.webkit.WebSettingsClassic");
            Method md = clsWebSettingsClassic.getMethod(
                    "setProperty", String.class, String.class);
            md.invoke(webView.getSettings(), "inverted", "true");
            md.invoke(webView.getSettings(), "inverted_contrast", "1");
        } catch (Exception e) {}


//        ZhihuClient.getZhihuApi().getEditorHtmlSource(id)
//                .compose(TransformUtils.<ResponseBody>defaultSchedulers())
//                .subscribe(new Observer<ResponseBody>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody value) {
//                        try {
//                            MyLog.e("html source ",value.string());
//                            String html = value.string();
//                            if (!TextUtils.isEmpty(html)){
//                                boolean nightMode = (boolean) SpUtils.get(EditorDetailActivity.this, Constant.KEY_NIGHT, false);
//                                boolean largeFont = (boolean) SpUtils.get(EditorDetailActivity.this, Constant.KEY_BIG_FONT, false);
//                                if (nightMode)
//                                    HtmlUtils.addToHtmlEnd(html,Constant.NIGHT_JS);
//                                if (largeFont)
//                                    HtmlUtils.addToHtmlEnd(html,Constant.BIGFONT_JS);
//                                webView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
//                            }else{
//                                webView.loadUrl(url);
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            webView.loadUrl(url);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        webView.loadUrl(url);
//                        MyLog.e("html source error ",e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }
}
