package com.mreturn.zhihudaily.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mreturn.zhihudaily.app.Constant;


/**
 * Created by mReturn
 * on 2017/5/22.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context) {
        super(context, Constant.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createStoryTable = "CREATE TABLE "
                + Constant.TABLE_STORY
                + "("
                + Constant.UID + " integer primary key autoincrement,"
                + Constant.ID + " int,"
                + Constant.TITLE + " text，"
                + Constant.IMAGE + " text,"
                + Constant.DATE + " text,"
                + Constant.MULTI_PIC + " int,"
                + Constant.TOP + " int,"
                + Constant.READ + " int"
                + ")";
        String createReadTAble = "CREATE TABLE "
                + Constant.TABLE_READ
                + "("
                + Constant.UID + " integer primary key autoincrement,"
                + Constant.ID + " int"
                + ")";
        String createpraiseTable = "CREATE TABLE "
                + Constant.TABLE_PRAISE
                + "("
                + Constant.UID + " integer primary key autoincrement,"
                + Constant.ID + " int"
                + ")";
        String createCollectTable = "CREATE TABLE "
                + Constant.TABLE_COLLECT
                + "("
                + Constant.UID + " integer primary key autoincrement,"
                + Constant.ID + " int,"
                + Constant.TITLE + " text,"
                + Constant.IMAGE + " text,"
                + Constant.MULTI_PIC + " int"
                + ")";

        sqLiteDatabase.execSQL(createStoryTable);
        sqLiteDatabase.execSQL(createReadTAble);
        sqLiteDatabase.execSQL(createpraiseTable);
        sqLiteDatabase.execSQL(createCollectTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
