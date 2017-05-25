package com.mreturn.zhihudaily.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mreturn.zhihudaily.app.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mReturn
 * on 2017/5/25.
 *
 * 点赞
 */

public class PraiseDao {
    private DataBaseHelper dbHelper;

    public PraiseDao(Context context){
        dbHelper = new DataBaseHelper(context);
    }

    public void save(int id) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.ID, id);
        database.insert(Constant.TABLE_PRAISE, null, contentValues);
        database.close();
    }

    public void delete(int id) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(Constant.TABLE_PRAISE, " id = ? ", new String[]{id + ""});
        database.close();
    }

    public List<Integer> getPraisetIdLis() {
        List<Integer> praiseList = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor query = null;
        try {
            database.beginTransaction();
            query = database.query(Constant.TABLE_PRAISE, new String[]{Constant.ID}, null, null, null, null, null);
            while (query.moveToNext()) {
                praiseList.add(query.getInt(query.getColumnIndex(Constant.ID)));
            }
            database.setTransactionSuccessful();
            database.endTransaction();
        } catch (Exception e) {
            database.endTransaction();
        } finally {
            if (query != null) {
                query.close();
            }
            database.close();
        }
        return praiseList;
    }
}
