package com.mreturn.zhihudaily.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mreturn.zhihudaily.app.Constant;
import com.mreturn.zhihudaily.app.ZhiHuApplication;
import com.mreturn.zhihudaily.model.StoryDetailBean;
import com.mreturn.zhihudaily.utils.MyLog;


/**
 * Created by mReturn
 * on 2017/6/5.
 */

public class DetailDao {
    private DataBaseHelper dbHelper;

    public DetailDao() {
        dbHelper = new DataBaseHelper(ZhiHuApplication.appContext);
    }

    public void saveContent(int detailId, StoryDetailBean storyDetail) {
        if (getStoryDetail(detailId) == null) {
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Constant.DETAIL_ID, detailId);
            contentValues.put(Constant.DETAIL_CONTENT, storyDetail.getBody());
            contentValues.put(Constant.TITLE, storyDetail.getTitle());
            contentValues.put(Constant.IMAGE, storyDetail.getImage());
            contentValues.put(Constant.IMG_SOURCE, storyDetail.getImage_source());
            database.insert(Constant.TABLE_DETAIL, null, contentValues);
            database.close();
            MyLog.e("cache: save detail:","detail----------");
        }
    }

    public StoryDetailBean getStoryDetail(int detailId) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.query(Constant.TABLE_DETAIL,
                    new String[]{Constant.DETAIL_CONTENT, Constant.TITLE, Constant.IMAGE, Constant.IMG_SOURCE},
                    Constant.DETAIL_ID + "=?",
                    new String[]{detailId + ""},
                    null, null, null);
            if (cursor.moveToNext()) {
                String content = cursor.getString(cursor.getColumnIndex(Constant.DETAIL_CONTENT));
                String title = cursor.getString(cursor.getColumnIndex(Constant.TITLE));
                String image = cursor.getString(cursor.getColumnIndex(Constant.IMAGE));
                String imgSource = cursor.getString(cursor.getColumnIndex(Constant.IMG_SOURCE));
                StoryDetailBean storyDetail = new StoryDetailBean(content, imgSource, title, image);
                return storyDetail;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }
        return null;
    }
}
