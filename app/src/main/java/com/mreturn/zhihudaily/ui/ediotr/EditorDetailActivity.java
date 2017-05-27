package com.mreturn.zhihudaily.ui.ediotr;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mreturn.zhihudaily.Presenter.BasePresenter;
import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.api.ZhihuClient;
import com.mreturn.zhihudaily.app.Constant;
import com.mreturn.zhihudaily.ui.BaseToolBarAtivity;
import com.mreturn.zhihudaily.utils.CommonUtils;
import com.mreturn.zhihudaily.utils.HtmlUtils;
import com.mreturn.zhihudaily.utils.MyLog;
import com.mreturn.zhihudaily.utils.SpUtils;
import com.mreturn.zhihudaily.utils.TransformUtils;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.mreturn.zhihudaily.utils.HtmlUtils.addToHtmlEnd;

/**
 * Created by mReturn
 * on 2017/5/24.
 */

public class EditorDetailActivity extends BaseToolBarAtivity {
    @BindView(R.id.wv_editor_detail)
    WebView webView;

    boolean nightMode;
    boolean largeFont;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editor_detail;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void prepareData(Intent intent) {
        super.prepareData(intent);
        nightMode = (boolean) SpUtils.get(EditorDetailActivity.this, Constant.KEY_NIGHT, false);
        largeFont = (boolean) SpUtils.get(EditorDetailActivity.this, Constant.KEY_BIG_FONT, false);
    }

    @Override
    protected void initView() {
        if (nightMode) {
            webView.setBackgroundColor(Color.parseColor("#333333"));
        }
        initBackToolBar(R.string.editor_info_title);
        CommonUtils.initWebView(this, webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                MyLog.e("editor1: ", url);
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
        final String url = String.format(Constant.EDITOR_DETAIL_URL, id);
        if (nightMode || largeFont) {
            getHtmlSoure(url);
        } else {
            webView.loadUrl(url);
        }
    }

    //获取网页源码 加入js
    private void getHtmlSoure(final String url) {
        ZhihuClient.getZhihuApi().getHtmlSource(url)
                .compose(TransformUtils.<String>defaultSchedulers())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String value) {
//                        MyLog.e("html source ", value);
                        String html = value;
                        MyLog.e("html source ", html);
                        if (!TextUtils.isEmpty(html)) {
                            if (nightMode)
                                html = HtmlUtils.addToHtmlEnd(html, Constant.NIGHT_JS);
                            if (largeFont)
                                html = addToHtmlEnd(html, Constant.BIGFONT_JS);
                            MyLog.e("html source replace", html);
                            webView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
                        } else {
                            webView.loadUrl(url);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        webView.loadUrl(url);
                        MyLog.e("html source error ", e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
