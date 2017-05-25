package com.mreturn.zhihudaily.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mreturn.zhihudaily.app.Constant;
import com.mreturn.zhihudaily.model.StoriesBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mReturn
 * on 2017/5/25.
 * 收藏
 */

public class CollectDao {
    private DataBaseHelper dbHelper;

    public CollectDao(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public boolean save(StoriesBean story) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.ID, story.getId());
        contentValues.put(Constant.TITLE, story.getTitle());
        contentValues.put(Constant.IMAGE, story.getImages() == null ? null : story.getImages().get(0));
        contentValues.put(Constant.MULTI_PIC, story.isMultipic() ? 1 : 0);
        long insert = database.insert(Constant.TABLE_COLLECT, null, contentValues);
        database.close();
        return insert != -1;
    }

    public boolean delete(StoriesBean story) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int i = database.delete(Constant.TABLE_COLLECT, " id=? ", new String[]{story.getId() + ""});
        database.close();
        return i != 0;
    }

    public List<StoriesBean> getCollectList() {
        List<StoriesBean> stories = new ArrayList<>(0);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.query(Constant.TABLE_COLLECT,
                    new String[]{Constant.ID, Constant.TITLE, Constant.IMAGE, Constant.MULTI_PIC},
                    null, null, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(Constant.ID));
                String title = cursor.getString(cursor.getColumnIndex(Constant.TITLE));
                String image = cursor.getString(cursor.getColumnIndex(Constant.IMAGE));
                int multiPic = cursor.getInt(cursor.getColumnIndex(Constant.MULTI_PIC));
                stories.add(new StoriesBean(id, title, image, null, multiPic == 1, false));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }

        return stories;
    }

    public List<Integer> getCollectIdList() {
        List<Integer> collectListId = new ArrayList<>(0);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.query(Constant.TABLE_COLLECT, new String[]{Constant.ID}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                collectListId.add(cursor.getInt(cursor.getColumnIndex(Constant.ID)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }

        return collectListId;
    }
}
