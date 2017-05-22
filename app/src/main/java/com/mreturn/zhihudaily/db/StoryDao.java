package com.mreturn.zhihudaily.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mreturn.zhihudaily.app.Constant;
import com.mreturn.zhihudaily.model.StoryListBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mReturn
 * on 2017/5/22.
 */

public class StoryDao {
    private DataBaseHelper dbHelper;

    public StoryDao(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public List<StoryListBean.TopStoriesBean> getTopStoryList() {
        List<StoryListBean.TopStoriesBean> topStories = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.query(Constant.TABLE_STORY,
                    new String[]{Constant.ID, Constant.TITLE, Constant.IMAGE, Constant.DATE, Constant.TOP},
                    "top=?", new String[]{"1"}, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(Constant.ID));
                String title = cursor.getString(cursor.getColumnIndex(Constant.TITLE));
                String image = cursor.getString(cursor.getColumnIndex(Constant.IMAGE));
                topStories.add(new StoryListBean.TopStoriesBean(id, title, image));
            }
            return topStories;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }
        return Collections.emptyList();
    }

    public List<StoryListBean.StoriesBean> getStoryList() {
        List<StoryListBean.StoriesBean> stories = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.query(Constant.TABLE_STORY,
                    new String[]{Constant.ID, Constant.TITLE, Constant.IMAGE, Constant.DATE, Constant.TOP,
                            Constant.MULTI_PIC, Constant.READ},
                    "top=?", new String[]{"0"}, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(Constant.ID));
                String title = cursor.getString(cursor.getColumnIndex(Constant.TITLE));
                String image = cursor.getString(cursor.getColumnIndex(Constant.IMAGE));
                String date = cursor.getString(cursor.getColumnIndex(Constant.DATE));
                int multipic = cursor.getInt(cursor.getColumnIndex(Constant.MULTI_PIC));
                int read = cursor.getInt(cursor.getColumnIndex(Constant.READ));
                stories.add(new StoryListBean.StoriesBean(id, title, image, date, multipic == 1, read == 1));
            }
            return stories;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }
        return Collections.emptyList();
    }

    public void saveStoryList(List<StoryListBean.StoriesBean> stories, List<StoryListBean.TopStoriesBean> topStories){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            database.beginTransaction();
            database.delete(Constant.TABLE_STORY,"1=1",null);
            for (StoryListBean.TopStoriesBean topStroy:topStories){
                ContentValues cv = new ContentValues();
                cv.put(Constant.ID,topStroy.getId());
                cv.put(Constant.TITLE,topStroy.getTitle());
                cv.put(Constant.IMAGE,topStroy.getImage());
                cv.put(Constant.TOP,1);
                long insert = database.insert(Constant.TABLE_STORY,null,cv);
                if (insert <= 0){  //error
                    database.endTransaction();
                }
            }
            for (StoryListBean.StoriesBean story:stories){
                ContentValues cv = new ContentValues();
                cv.put(Constant.ID,story.getId());
                cv.put(Constant.TITLE,story.getTitle());
                cv.put(Constant.IMAGE,story.getImages().get(0));
                cv.put(Constant.DATE,story.getDate());
                cv.put(Constant.TOP,0);
                cv.put(Constant.MULTI_PIC,story.isMultipic()?1:0);
                cv.put(Constant.READ,story.isRead()?1:0);
                long insert = database.insert(Constant.TABLE_STORY,null,cv);
                if (insert <= 0){
                    database.endTransaction();
                }
            }
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            database.close();
        }
    }
}
