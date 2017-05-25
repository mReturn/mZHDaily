package com.mreturn.zhihudaily.ui.story;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
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
import com.mreturn.zhihudaily.utils.ImageLoader;
import com.mreturn.zhihudaily.utils.MyLog;
import com.mreturn.zhihudaily.utils.SpUtils;
import com.mreturn.zhihudaily.utils.ToastShow;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class StoryDetailActivity extends BaseToolBarAtivity implements DetailView {

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
    String defaultImgAttr = "reimg-src";

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
        CommonUtils.initWebView(this, webView);
        //添加js
        webView.addJavascriptInterface(this, "ZhihuDaily");
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
        if (collectList.contains(storyID)) {
            collectItem.setIcon(R.drawable.collect);
            collectItem.setTitle(collected);
        }

        final List<Integer> praiseList = detailPresenter.getPraiseIdList(this);
        if (praiseList.contains(storyID)) {
            praiseItem.setIcon(R.drawable.praised);
            praiseItem.setTitle(praised);
            ivPraise.setContentDescription("praised");
        }

        praiseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("praise".equals(ivPraise.getContentDescription())) {
                    ivPraise.setContentDescription("praised");
                    ivPraise.setImageResource(R.drawable.praised);
                    detailPresenter.savePraise(StoryDetailActivity.this, storyID);
                } else {
                    ivPraise.setContentDescription("praise");
                    ivPraise.setImageResource(R.drawable.praise);
                    detailPresenter.removePraise(StoryDetailActivity.this, storyID);
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                ToastShow.show("share");
                break;
            case R.id.menu_collect:
                if (collect.equals(item.getTitle())) {
                    detailPresenter.collectStory(this, story);
                } else {
                    detailPresenter.removeCollect(this, story);
                }
                break;
            case R.id.menu_comment:
                ToastShow.show("to comment");
                break;
            case R.id.menu_praise:
                if (ivPraise != null) {
                    if ("praise".equals(ivPraise.getContentDescription())) {
                        ivPraise.setContentDescription("praised");
                        ivPraise.setImageResource(R.drawable.praised);
                        detailPresenter.savePraise(StoryDetailActivity.this, storyID);
                    } else {
                        ivPraise.setContentDescription("praise");
                        ivPraise.setImageResource(R.drawable.praise);
                        detailPresenter.removePraise(StoryDetailActivity.this, storyID);
                    }
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoadingView(boolean show) {
        clProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showDetail(StoryDetailBean storyDetail) {
        setToolBarTitle("");
        if (TextUtils.isEmpty(storyDetail.getImage())) {
            ivTop.setVisibility(View.GONE);
        } else {
            ivTop.setVisibility(View.VISIBLE);
            tvTitle.setText(storyDetail.getTitle());
            tvSource.setText(storyDetail.getImage_source());
            ImageLoader.display(this, ivTop, storyDetail.getImage());
        }

        loadHtml(storyDetail);
    }

    //加载网页内容
    private void loadHtml(StoryDetailBean storyDetail) {
        StringBuilder htmlBuilder = new StringBuilder("<!doctype html>\n<html><head>\n<meta charset=\"utf-8\">\n" +
                "\t<meta name=\"viewport\" content=\"width=device-width,user-scalable=no\">");
        String content = storyDetail.getBody();
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">\n";
        String img_replace = "<script src=\"file:///android_asset/img_replace.js\"></script>\n";
        String video = "<script src=\"file:///android_asset/video.js\"></script>\n";
        String zepto = "<script src=\"file:///android_asset/zepto.min.js\"></script>\n";
        String autoLoadImage = "onload=\"onLoaded()\"";

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
            String night = "<script src=\"file:///android_asset/night.js\"></script>\n";
            htmlBuilder.append(night);
        }
        if (largeFont) {
            String bigFont = "<script src=\"file:///android_asset/large-font.js\"></script>\n";
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
                e.attr("reimg-src", imgUrl);
                e.attr("onclick", "onImageClick(this)");
            }
        }
        return doc.html();
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


    @JavascriptInterface
    public void clickToLoadImage(String imgPath) {
        MyLog.e("click load img: ", imgPath);
    }

    @JavascriptInterface
    public void loadImage(final String imgPath) {
        MyLog.e("load img: ", imgPath);
        webView.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(StoryDetailActivity.this).load(imgPath)
                        .downloadOnly(new SimpleTarget<File>() {
                            @Override
                            public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                                String str = "file://" + resource.getAbsolutePath();//加载完成的图片地址
                                try {
                                    String[] paramArray = new String[2];
                                    paramArray[0] = URLEncoder.encode(imgPath, "UTF-8");
                                    paramArray[1] = str;
                                    onImageLoadingComplete("onImageLoadingComplete", paramArray);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    @JavascriptInterface
    public void openImage(String imgPath) {
        ToastShow.show("open img");
        MyLog.e("open img: ", imgPath);
    }

    public void onImageLoadingComplete(String funName, String[] paramArray) {
        String str = "'" + TextUtils.join("','", paramArray) + "'";
        webView.loadUrl("javascript:" + funName + "(" + str + ");");
    }
}
