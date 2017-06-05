package com.mreturn.zhihudaily.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.mreturn.zhihudaily.app.Constant;
import com.mreturn.zhihudaily.app.ZhiHuApplication;


/**
 * Created by mReturn
 * on 2017/6/5.
 */

public class ImgDao {
    private DataBaseHelper dbHelper;

    public ImgDao() {
        dbHelper = new DataBaseHelper(ZhiHuApplication.appContext);
    }

    public void saveImgUri(String imgUrl, String imgUri) {
        if (TextUtils.isEmpty(getImgUri(imgUrl))) {
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Constant.IMAGE, imgUrl);
            contentValues.put(Constant.IMG_URI, imgUri);
            database.insert(Constant.TABLE_IMG, null, contentValues);
            database.close();
        }
    }

    public String getImgUri(String imgUrl) {
        if (TextUtils.isEmpty(imgUrl)) return null;
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.query(Constant.TABLE_DETAIL,
                    new String[]{Constant.IMG_URI},
                    Constant.IMAGE + "=?",
                    new String[]{imgUrl},
                    null, null, null);
            if (cursor.moveToNext()) {
                return cursor.getString(cursor.getColumnIndex(Constant.IMG_URI));
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
