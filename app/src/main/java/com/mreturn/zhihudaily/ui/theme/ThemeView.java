package com.mreturn.zhihudaily.ui.theme;

import com.mreturn.zhihudaily.model.StoriesBean;
import com.mreturn.zhihudaily.model.ThemeBean;

import java.util.List;

/**
 * Created by mReturn
 * on 2017/5/23.
 */

public interface ThemeView {
    void setRefreshing(boolean b);

    void showTheme(ThemeBean themeBean);

    void showMoreThemeStory(List<StoriesBean> stories);

    void showGetThemeError();

    void showLoadMoreError();

    void setLoadMoreViewShow(boolean show);

    void showNoMoreData();

    void setLastItemId(int id);
}
