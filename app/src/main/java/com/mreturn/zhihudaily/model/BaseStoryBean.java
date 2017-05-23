package com.mreturn.zhihudaily.model;

/**
 * Created by mReturn
 * on 2017/5/19.
 */

public class BaseStoryBean {
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_HEADER = 2;
    public static final int TYPE_FOOTER = 3;

    protected int showType;

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }
}
