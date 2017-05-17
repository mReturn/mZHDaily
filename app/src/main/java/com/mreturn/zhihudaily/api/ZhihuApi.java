package com.mreturn.zhihudaily.api;

import com.mreturn.zhihudaily.model.SplashData;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by mReturn
 * on 2017/5/16.
 */

public interface ZhihuApi {
   @GET("prefetch-launch-images/720*1016")
   Observable<SplashData> getSplashData();

}

