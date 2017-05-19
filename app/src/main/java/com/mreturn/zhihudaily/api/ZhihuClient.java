package com.mreturn.zhihudaily.api;

import android.text.TextUtils;

import com.mreturn.zhihudaily.app.ZhiHuApplication;
import com.mreturn.zhihudaily.app.Constant;
import com.mreturn.zhihudaily.utils.NetUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mReturn
 * on 2017/5/16.
 */

public class ZhihuClient {
    private static ZhihuApi mZhihuApi;
    private static OkHttpClient mhttpClient;
    private static Converter.Factory mConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory mCallAdapterFactory = RxJava2CallAdapterFactory.create();

    private static final int DEFAULT_TIMEOUT = 10;
    private static final int DEFAULT_CACHE_SIZE = 10 *1024 *1024;

    private static final Interceptor REWRITE_CACHE_CONTROLINTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            if (NetUtils.isNetAvailable(ZhiHuApplication.appContext)){
                int maxAge = 60; //在线缓存可读取时间（s）
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control","public,max-age=" + maxAge)
                        .build();

            }else{
                int maxStale = 60 * 60 * 24 * 28; //离线缓存保存4周
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control","public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    public static ZhihuApi getZhihuApi(){
        return getZhihuApi("");
    }
    public static ZhihuApi getZhihuApi(String basUrl){
        String mBaseUrl = TextUtils.isEmpty(basUrl)? Constant.BASE_URL:basUrl;
        if (mZhihuApi == null){
            if (mhttpClient == null){
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                //超时时间
                builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                builder.readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);
                builder.writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);
                //添加缓存
                File cacheFile = new File(ZhiHuApplication.appContext.getCacheDir(),"okHttpCache");
                builder.cache(new Cache(cacheFile,DEFAULT_CACHE_SIZE));
                //添加拦截器
                builder.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (!NetUtils.isNetAvailable(ZhiHuApplication.appContext)){
                            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                        }
                        return chain.proceed(request);
                    }
                });
                builder.addNetworkInterceptor(REWRITE_CACHE_CONTROLINTERCEPTOR)
                        .addNetworkInterceptor(REWRITE_CACHE_CONTROLINTERCEPTOR);
                mhttpClient = builder.build();
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .client(mhttpClient)
                    .baseUrl(mBaseUrl)
                    .addConverterFactory(mConverterFactory)
                    .addCallAdapterFactory(mCallAdapterFactory)
                    .build();
            mZhihuApi = retrofit.create(ZhihuApi.class);
        }
        return mZhihuApi;
    }
}
