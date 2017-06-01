package com.mreturn.zhihudaily.utils;

import android.os.Environment;
import android.text.format.Formatter;

import com.mreturn.zhihudaily.app.ZhiHuApplication;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by mReturn
 * on 2017/6/1.
 */

public class CacheUtils {
    /**
     * 获取总缓存大小
     *
     * @return cacheDir目录下文件总大小
     */
    public static String getTotalCacheSize() {
        long cacheSize = getFolderSize(ZhiHuApplication.appContext.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(ZhiHuApplication.appContext.getExternalCacheDir());
        }
        return Formatter.formatFileSize(ZhiHuApplication.appContext, cacheSize);
    }

    /**
     * 清除所有缓存
     */
    public static void cleanAllCache() {
        deleteDir(ZhiHuApplication.appContext.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(ZhiHuApplication.appContext.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File cacheDir) {
        if (cacheDir != null && cacheDir.isDirectory()) {
            String[] children = cacheDir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(cacheDir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        return cacheDir != null && cacheDir.delete();
    }

    @Deprecated
    private static String getFormatSize(long cacheSize) {
        double kiloByte = cacheSize / 1024;
        if (kiloByte < 1) {
            return cacheSize + "B";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "K";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "M";
        }
        double teraByte = gigaByte / 1024;
        if (teraByte < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(megaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "G";

        }
        return "没有缓存";
    }

    private static long getFolderSize(File file) {
        long size = 0;
        File[] fileList = file.listFiles();
        for (File aFileList : fileList) {
            if (aFileList.isDirectory()) {
                size = size + getFolderSize(aFileList);
            } else {
                size = size + aFileList.length();
            }
        }
        return size;
    }
}
