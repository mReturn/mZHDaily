package com.mreturn.zhihudaily.utils;

/**
 * Created by mReturn
 * on 2017/5/26.
 */

public class HtmlUtils {
    public static String addToHtmlEnd(String html,String addStr){
        String startStr = html.substring(0,html.indexOf("</body>"));
        String endStr = html.substring(html.indexOf("</body>"),html.length());
        return startStr+addStr+endStr;
    }
}
