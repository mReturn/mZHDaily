package com.mreturn.zhihudaily.ui.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.mreturn.zhihudaily.Presenter.BasePresenter;
import com.mreturn.zhihudaily.Presenter.StoryDetailPresenter;
import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.app.Constant;
import com.mreturn.zhihudaily.model.StoriesBean;
import com.mreturn.zhihudaily.model.StoryDetailBean;
import com.mreturn.zhihudaily.model.StoryExtraBean;
import com.mreturn.zhihudaily.ui.BaseToolBarAtivity;
import com.mreturn.zhihudaily.utils.CommonUtils;
import com.mreturn.zhihudaily.utils.MyLog;
import com.mreturn.zhihudaily.utils.SpUtils;
import com.mreturn.zhihudaily.utils.ToastShow;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by mReturn
 * on 2017/5/26.
 */

public abstract class BaseDetailActiivty extends BaseToolBarAtivity implements DetailView{

    @BindView(R.id.cl_pb)
    ContentLoadingProgressBar clProgressBar;
    @BindView(R.id.web_story_detail)
    WebView webView;

    protected StoryDetailPresenter detailPresenter = new StoryDetailPresenter(this);

    MenuItem collectItem;
    MenuItem praiseItem;
    View commentView;
    View praiseView;
    TextView tvComment;
    TextView tvPraise;
    ImageView ivPraise;

    StoriesBean story;
    int storyID;
    String collect = "收藏";
    String collected = "已收藏";
    String praise = "点赞";
    String praised = "已赞";
    List<String> imgUrlList;
    String defaultImgAttr = "zhimg-src";
    protected StoryDetailBean storyDetail;

    @Override
    protected BasePresenter createPresenter() {
        return detailPresenter;
    }

    @Override
    protected void initView() {
        initBackToolBar(null);
        CommonUtils.initWebView(this, webView);
        //添加js
        webView.addJavascriptInterface(this, "ZhihuDaily");

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
        story = getIntent().getParcelableExtra(Constant.STORY);
        storyID = story.getId();
        detailPresenter.getStoryDetail(storyID);
//        detailPresenter.getStoryExtra(storyID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MyLog.e("detail ", "oncreate menu");
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        collectItem = menu.findItem(R.id.menu_collect);
        praiseItem = menu.findItem(R.id.menu_praise);
        praiseItem.setActionView(R.layout.action_menu_praise);
        praiseView = praiseItem.getActionView();
        tvPraise = (TextView) praiseView.findViewById(R.id.tv_praise);
        ivPraise = (ImageView) praiseView.findViewById(R.id.iv_praise);

        menu.findItem(R.id.menu_comment).setActionView(R.layout.action_menu_comment);
        commentView = menu.findItem(R.id.menu_comment).getActionView();
        tvComment = (TextView) commentView.findViewById(R.id.tv_comment);

        //先初始化menu 避免空指针
        detailPresenter.getStoryExtra(storyID);

        List<Integer> collectList = detailPresenter.getCollectIdList(this);
        MyLog.e("detail: collectList ",collectList.size()+"");
        if (collectList.contains(storyID)) {
            collectItem.setIcon(R.drawable.collected);
            collectItem.setTitle(collected);
        }

        final List<Integer> praiseList = detailPresenter.getPraiseIdList(this);
        MyLog.e("detail: praiseList ",praiseList.size()+"");
        if (praiseList.contains(storyID)) {
            praiseItem.setTitle(praised);
            ivPraise.setImageResource(R.drawable.praised);
            ivPraise.setContentDescription(praised);
        }

        commentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastShow.show("to comment");
            }
        });

        praiseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickPraise();
            }
        });

        return true;
    }

    //点赞按钮点击
    private void clickPraise() {
        int praiseNum = -1;
        try {
            praiseNum = Integer.parseInt(tvPraise.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if ("praise".equals(ivPraise.getContentDescription())) {
            ivPraise.setContentDescription("praised");
            ivPraise.setImageResource(R.drawable.praised);
            detailPresenter.savePraise(BaseDetailActiivty.this, storyID);
            if (praiseNum > -1)
                tvPraise.setText((praiseNum+1)+"");
        } else {
            ivPraise.setContentDescription("praise");
            ivPraise.setImageResource(R.drawable.praise);
            detailPresenter.removePraise(BaseDetailActiivty.this, storyID);
            if (praiseNum > 0)
                tvPraise.setText((praiseNum-1)+"");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                if (storyDetail != null){
                    showShare(storyDetail.getShare_url());
                }
                break;
            case R.id.menu_collect:
                if (collect.equals(item.getTitle())) {
                    detailPresenter.collectStory(this, story);
                } else {
                    detailPresenter.removeCollect(this, story);
                }
                break;
            case R.id.menu_praise:
                if (ivPraise != null) {
                    if ("praise".equals(ivPraise.getContentDescription())) {
                        ivPraise.setContentDescription("praised");
                        ivPraise.setImageResource(R.drawable.praised);
                        detailPresenter.savePraise(BaseDetailActiivty.this, storyID);
                    } else {
                        ivPraise.setContentDescription("praise");
                        ivPraise.setImageResource(R.drawable.praise);
                        detailPresenter.removePraise(BaseDetailActiivty.this, storyID);
                    }
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showShare(String share_url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, share_url);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "分享到"));
    }

    @Override
    public void showLoadingView(boolean show) {
        clProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showExtra(StoryExtraBean storyExtra) {
        MyLog.e("detail ", "show extra");
        tvComment.setText(formatNum(storyExtra.getComments()));
        tvPraise.setText(formatNum(storyExtra.getPopularity()));
    }

    private String formatNum(int num) {
        if (num < 1000) {
            return num + "";
        }
        String str = (num / 1000) + "";
        if (str.length() > 3) {
            return str.substring(0, str.length() - 1) + "k";
        } else {
            return str + "k";
        }
    }

    @Override
    public void showLoadFail() {
        ToastShow.show("加载失败");
    }

    @Override
    public void setCollectState(boolean isCollected) {
        if (isCollected) {
            collectItem.setTitle(collected);
            collectItem.setIcon(R.drawable.collected);
        } else {
            collectItem.setTitle(collect);
            collectItem.setIcon(R.drawable.collect);
        }

    }

    @Override
    public void showCollectFail() {
        ToastShow.show("收藏失败");
    }

    @Override
    public void showUnCollectFail() {
        ToastShow.show("取消收藏失败");
    }


    //加载网页内容
    protected void loadHtml(StoryDetailBean storyDetail) {
        StringBuilder htmlBuilder = new StringBuilder("<!doctype html>\n<html><head>\n<meta charset=\"utf-8\">\n" +
                "\t<meta name=\"viewport\" content=\"width=device-width,user-scalable=no\">");
        String content = storyDetail.getBody();
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">\n";
        String img_replace = "<script src=\"file:///android_asset/img_replace.js\"></script>\n";
        String video = "<script src=\"file:///android_asset/video.js\"></script>\n";
        String zepto = "<script src=\"file:///android_asset/zepto.min.js\"></script>\n";
        String autoLoadImage = "onload=\"onLoaded()\"";
        String night = "<script src=\"file:///android_asset/night.js\"></script>\n";
        String bigFont = "<script src=\"file:///android_asset/large-font.js\"></script>\n";

        //是否自动加载图片
        boolean autoLoad = true;
        boolean nightMode = (boolean) SpUtils.get(this, Constant.KEY_NIGHT, false);
        boolean largeFont = (boolean) SpUtils.get(this, Constant.KEY_BIG_FONT, false);
        htmlBuilder.append(css)
                .append(zepto)
                .append(img_replace)
                .append(video)
                .append("</head><body className=\"\"")
                .append(autoLoad ? autoLoadImage : "")
                .append(" >")
                .append(content);
        if (nightMode) {
            htmlBuilder.append(night);
        }
        if (largeFont) {
            htmlBuilder.append(bigFont);
        }
        htmlBuilder.append("</body></html>");
        String html = htmlBuilder.toString();
        html = html.replace("<div class=\"img-place-holder\">", "");
        html = replaceImgTagFromHTML(html, autoLoad, nightMode);
        Log.e("html2", html);
        webView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }

    //替换 html 中img标签属性
    private String replaceImgTagFromHTML(String html, boolean autoLoad, boolean nightMode) {
        imgUrlList = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Elements es = doc.getElementsByTag("img");
        for (Element e : es) {
            if (!"avatar".equals(e.attr("class"))) {
                String imgUrl = e.attr("src");
                imgUrlList.add(imgUrl);
                String src = String.format("file:///android_asset/default_pic_content_image_%s_%s.png",
                        autoLoad ? "loading" : "download",
                        nightMode ? "dark" : "light");
                e.attr("src", src);
                e.attr(defaultImgAttr, imgUrl);
                e.attr("onclick", "onImageClick(this)");
            }
        }
        return doc.html();
    }


    // ======================= js ========================
    @JavascriptInterface
    public void clickToLoadImage(final String imgPath) {
        MyLog.e("detail: ","click load img");
        if (TextUtils.isEmpty(imgPath))
            return;
        webView.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(BaseDetailActiivty.this).load(imgPath)
                        .downloadOnly(new SimpleTarget<File>() {
                            @Override
                            public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                                String str = "file://" + resource.getAbsolutePath();//加载完成的图片地址
                                try {
                                    String[] arrayOfString = new String[2];
                                    arrayOfString[0] = URLEncoder.encode(imgPath,"UTF-8");//旧url
                                    arrayOfString[1] = str;
                                    onImageLoadingComplete("onImageLoadingComplete", arrayOfString);
                                } catch (Exception e) {

                                }
                            }
                        });
            }
        });
    }

    @JavascriptInterface
    public void loadImage(final String imgPath) {
        MyLog.e("detail: ","load img");
        if (TextUtils.isEmpty(imgPath))
            return;
        webView.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(BaseDetailActiivty.this).load(imgPath)
                        .downloadOnly(new SimpleTarget<File>() {
                            @Override
                            public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                                String str = "file://" + resource.getAbsolutePath();//加载完成的图片地址
                                try {
                                    String[] arrayOfString = new String[2];
                                    arrayOfString[0] = URLEncoder.encode(imgPath,"UTF-8");//旧url
                                    arrayOfString[1] = str;
                                    onImageLoadingComplete("onImageLoadingComplete", arrayOfString);
                                } catch (Exception e) {
                                }
                            }
                        });
            }
        });
    }

    @JavascriptInterface
    public void openImage(String imgPath) {
        MyLog.e("detail","open img");
        Intent intent = new Intent(this,ImgGalleryactivity.class);
        intent.putExtra(Constant.IMG_URL,imgPath);
        intent.putExtra(Constant.IMG_URL_LIST, (Serializable) imgUrlList);
        startActivity(intent);
    }

    public final void onImageLoadingComplete(String funName, String[] paramArray) {
        MyLog.e("detail: ","img lode complete");
        String str = "'" + TextUtils.join("','", paramArray) + "'";
        webView.loadUrl("javascript:" + funName + "(" + str + ");");
    }
}
