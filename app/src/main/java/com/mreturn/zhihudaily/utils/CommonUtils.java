package com.mreturn.zhihudaily.utils;

import android.content.Context;

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
     * @return yyyyMMdd
     */
    public static String getToday(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        return format.format(new Date());
    }

    /**
     * dp 转换为 px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px (Context context,float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取中文格式日期
     * @param date
     * @return  yyyy年MM月dd日 星期..
     */
    public static String getCnDate(String date){
        if (date.length() < 8)
            return "";
        String year = date.substring(0,4);
        String month = date.substring(4,6);
        String day = date.substring(6,8);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Integer.valueOf(year),Integer.valueOf(month)-1,Integer.valueOf(day));
        String weekDay = new SimpleDateFormat("EEEE",Locale.CHINA).format(calendar.getTime());
        return year + "年" + month + "月" + day + "日 " + weekDay;

    }
}
