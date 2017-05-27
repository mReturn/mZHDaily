package com.mreturn.zhihudaily.app;

import android.os.Environment;

/**
 * Created by mReturn
 * on 2017/5/17.
 */

public class Constant {
    //api
    public static final String BASE_URL = "http://news-at.zhihu.com/api/4/";
    public static final String BASE_SPLASH_URL = "http://news-at.zhihu.com/api/7/";
    public static final String EDITOR_DETAIL_URL = BASE_URL+"editor/%s/profile-page/android";
    public static final String EDITOR_URL = "editor/%s/profile-page/android";

    //sharedpreference
    public static final String IS_APP_OPENED = "is_app_opened";
    public static final String SPLASH_IMG_PATH = "splash_img_path";
    public static final String KEY_START_IMG_TEXT = "key_start_img_text";
    public static final String KEY_TODAY = "key_today";
    public static final String KEY_NIGHT = "key_night";
    public static final String KEY_HAS_OPEN_APP = "key_has_open_app";
    public static final String KEY_CURRENT_DATE = "key_current_date";
    public static final String KEY_BIG_FONT = "key_big_font";
    public static final String KEY_NO_LOAD_IMAGE = "key_no_load_image";
    public static final String KEY_HAS_CACHE = "key_has_cache";

    //fragment
    public static final String KEY_TITLE = "title";
    public static final String KEY_TAG = "tag";
    public static final String TAG_MAIN = "main";

    //database
    public static final String DB_NAME = "zhihu.db";
    public static final String TABLE_STORY = "t_story";
    public static final String TABLE_READ = "t_read";
    public static final String TABLE_COLLECT = "t_collect";
    public static final String TABLE_PRAISE = "t_praise";
    public static final String UID = "uid";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String IMAGE = "image";
    public static final String DATE = "date";
    public static final String MULTI_PIC = "multi_pic";
    public static final String TOP = "top";
    public static final String READ = "read";

    //intent
    public static final String STORY_ID = "story_id";
    public static final String THEME = "theme";
    public static final String STORY = "story";
    public static final String COMMENT_COUNT = "comment_count";
    public static final String LONG_COMMENT_COUNT = "long_comment_count";
    public static final String SHORT_COMMENT_COUNT = "short_comment_count";
    public static final String EDITOR_ID = "editor_id";
    public static final String IMG_URL = "img_url";
    public static final String IMG_URL_LIST = "img_url_list";

    //js
    public static final String NIGHT_JS= "<script src=\"file:///android_asset/night.js\"></script>\n";
    public static final String BIGFONT_JS = "<script src=\"file:///android_asset/large-font.js\"></script>\n";

    //sd
    public static final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() ;
    public static final String DOWNLOAD_PATH = SD_PATH+ "/_ZhiHuDaily/download/";
}
