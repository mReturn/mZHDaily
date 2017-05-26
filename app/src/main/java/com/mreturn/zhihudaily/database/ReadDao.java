package com.mreturn.zhihudaily.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mreturn.zhihudaily.app.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mReturn
 * on 2017/5/22.
 * 已读文章 db
 */

public class ReadDao {
    private DataBaseHelper dbHelper;

    public ReadDao(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public void save(int id) {
        if (!getReadList().contains(id)){
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Constant.ID, id);
            database.insert(Constant.TABLE_READ, null, contentValues);
            database.close();
        }
    }

    public List<Integer> getReadList() {
        List<Integer> readList = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            database.beginTransaction();
            cursor = database.query(Constant.TABLE_READ, new String[]{Constant.ID}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                readList.add(cursor.getInt(cursor.getColumnIndex(Constant.ID)));
            }
            database.setTransactionSuccessful();
            database.endTransaction();
            return readList;
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
}
