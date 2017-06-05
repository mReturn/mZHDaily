package com.mreturn.zhihudaily.Presenter;

import android.text.TextUtils;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.mreturn.zhihudaily.api.ZhihuClient;
import com.mreturn.zhihudaily.app.ZhiHuApplication;
import com.mreturn.zhihudaily.database.DetailDao;
import com.mreturn.zhihudaily.database.ImgDao;
import com.mreturn.zhihudaily.model.StoriesBean;
import com.mreturn.zhihudaily.model.StoryDetailBean;
import com.mreturn.zhihudaily.model.StoryListBean;
import com.mreturn.zhihudaily.utils.CommonUtils;
import com.mreturn.zhihudaily.utils.MyLog;
import com.mreturn.zhihudaily.utils.NetUtils;
import com.mreturn.zhihudaily.utils.ToastShow;
import com.mreturn.zhihudaily.utils.TransformUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by mReturn
 * on 2017/6/5.
 */

public class OffLineCachePresenter extends BasePresenter {
    TextView tv;

    List<StoriesBean> stories = new ArrayList<>();

    int downloadedNum;

    public OffLineCachePresenter(TextView tv) {
        this.tv = tv;
    }

    public void startDownload() {
        if (NetUtils.isNetAvailable(ZhiHuApplication.appContext)) {
            tv.setText("0%");
            getLatest();
        } else {
            ToastShow.show("网络异常");
        }
    }

    public void getLatest() {
        ZhihuClient.getZhihuApi().getLatest()
                .compose(TransformUtils.<StoryListBean>defaultSchedulers())
                .subscribe(new Observer<StoryListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(StoryListBean storyList) {
                        if (storyList != null && storyList.getStories() != null && storyList.getStories().size() > 0) {
                            stories.addAll(storyList.getStories());
                            getBefore(CommonUtils.getToday());
                        } else {
                            ToastShow.show("下载失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        MyLog.e("latest error: ", e.toString());
                        ToastShow.show("下载失败");
                    }

                    @Override
                    public void onComplete() {
                    }
                })
        ;
    }

    public void getBefore(String date) {
        ZhihuClient.getZhihuApi().loadMore(date)
                .compose(TransformUtils.<StoryListBean>defaultSchedulers())
                .subscribe(new Observer<StoryListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(StoryListBean storyList) {
                        if (storyList != null && storyList.getStories() != null && storyList.getStories().size() > 0) {
                            stories.addAll(storyList.getStories());
                        }
                        MyLog.e("offline: ", "next");

                    }

                    @Override
                    public void onError(Throwable e) {
                        MyLog.e("offline: ", "error");
                    }

                    @Override
                    public void onComplete() {
                        MyLog.e("offline: ", "complete");
                        for (int i = 0; i < stories.size(); i++) {
                            getStoryDetail(stories.get(i).getId());
                            downloadImg(stories.get(i).getImages());
                        }
                    }
                });
    }

    public void getStoryDetail(final int storyId) {
        ZhihuClient.getZhihuApi().getStoryDetail(storyId)
                .compose(TransformUtils.<StoryDetailBean>defaultSchedulers())
                .subscribe(new Observer<StoryDetailBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(StoryDetailBean storyDetail) {
                        if (storyDetail == null)
                            return;
                        downloadedNum += 1;
                        if ("100".equals(getPercent())) {
                            tv.setText("完成");
                        } else {
                            tv.setText(getPercent() + "%");
                        }
                        DetailDao detailDao = new DetailDao();
                        detailDao.saveContent(storyId, storyDetail);
                        downloadImg(storyDetail.getImage());
                        cacheImgFromHTML(storyDetail.getBody());
                    }

                    @Override
                    public void onError(Throwable e) {
                        MyLog.e("offline cache error ",e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void cacheImgFromHTML(String html) {
        if (TextUtils.isEmpty(html)) return;
        Document doc = Jsoup.parse(html);
        Elements es = doc.getElementsByTag("img");
        for (Element e : es) {
            String imgUrl = e.attr("src");
            downloadImg(imgUrl);
        }
    }

    private void downloadImg(List<String> imgList) {
        if (imgList != null && imgList.size() > 0) {
            for (String imgUrl : imgList) {
                downloadImg(imgUrl);
            }
        }
    }

    private void downloadImg(final String imgUrl) {
        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.with(ZhiHuApplication.appContext)
                    .load(imgUrl)
                    .downloadOnly(new SimpleTarget<File>() {
                        @Override
                        public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                            ImgDao imgDao = new ImgDao();
                            imgDao.saveImgUri(imgUrl, resource.getAbsolutePath());
                        }
                    });
        }
    }

    public String getPercent() {
        float num = (float) downloadedNum / stories.size();
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        String result = Float.parseFloat(df.format(num)) * 100 + "";
        return result.substring(0, result.indexOf("."));
    }
}
