package com.mreturn.zhihudaily.utils;

import java.text.SimpleDateFormat;
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
}
