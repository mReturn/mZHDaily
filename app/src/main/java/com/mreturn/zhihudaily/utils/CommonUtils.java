package com.mreturn.zhihudaily.utils;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mReturn
 * on 2017/5/18.
 */

public class CommonUtils {

    /**
     * 获取当前日期
     *
     * @return yyyyMMdd
     */
    public static String getToday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        return format.format(new Date());
    }

    /**
     * dp 转换为 px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取中文格式日期
     *
     * @param date
     * @return yyyy年MM月dd日 星期..
     */
    public static String getCnDate(String date) {
        if (date.length() < 8)
            return "";
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Integer.valueOf(year), Integer.valueOf(month) - 1, Integer.valueOf(day));
        String weekDay = new SimpleDateFormat("EEEE", Locale.CHINA).format(calendar.getTime());
        return year + "年" + month + "月" + day + "日 " + weekDay;

    }


    /**
     * 时间戳转换为日期格式
     * @param time
     * @return  MM-dd HH:mm
     */
    public static String timestamp2Date(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
        return sdf.format(new Date(time * 1000));
    }

    public static void initWebView(Context context, WebView webView) {
        WebSettings webSettings = webView.getSettings();
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//        //缩放操作
//        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
//        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
//        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //缓存
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(context.getDir("cache", 0).getPath());
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDomStorageEnabled(true);
        //其他
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptEnabled(true);  //设置支持Javascript
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

    }
}
