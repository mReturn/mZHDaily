package com.mreturn.zhihudaily.api;

import com.mreturn.zhihudaily.model.SplashBean;
import com.mreturn.zhihudaily.model.StoryDetailBean;
import com.mreturn.zhihudaily.model.StoryExtraBean;
import com.mreturn.zhihudaily.model.StoryListBean;
import com.mreturn.zhihudaily.model.ThemeBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mReturn
 * on 2017/5/16.
 */

public interface ZhihuApi {
   //开屏图
   @GET("prefetch-launch-images/720*1016")
   Observable<SplashBean> getSplashData();

   //今日新闻
   @GET("news/latest")
   Observable<StoryListBean> getLatest();

   //加载更多
   @GET("news/before/{date}")
   Observable<StoryListBean> loadMore(@Path("date") String date);

   //获取某个主题日报内容列表
   @GET("theme/{id}")
   Observable<ThemeBean> getTheme(@Path("id") int id);

   //加载更多主题内容
   @GET("theme/{theme_id}/before/{story_id}")
   Observable<ThemeBean> getMoreTheme(@Path("theme_id") int themeId, @Path("story_id") int storyId);

   //详情
   @GET("story/{id}")
   Observable<StoryDetailBean> getStoryDetail(@Path("id") int id);

   //附加数据（点赞数，评论数)
   @GET("story-extra/{id}")
   Observable<StoryExtraBean> getStoryExtra(@Path("id") int id);

   //获取html源码
   @GET("editor/{id}/profile-page/android")
   Observable<ResponseBody> getEditorHtmlSource(@Path("id") String id);

}

