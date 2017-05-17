package com.mreturn.zhihudaily.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by mReturn
 * on 2017/5/17.
 */

public class SpUtils {
    private static final String SP_NAME = "sp_zhihu";

    /**
     * 保存数据到 SharedPreferences
     *
     * @param context
     * @param key
     * @param object  要保存的数据值
     */
    public static void put(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 从SharedPreferences 读取数据
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object get(Context context,String key,Object defaultValue){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        if (defaultValue instanceof String){
            return sp.getString(key, (String) defaultValue);
        }else if (defaultValue instanceof Integer){
            return sp.getInt(key, (Integer) defaultValue);
        }else if (defaultValue instanceof Boolean){
            return sp.getBoolean(key, (Boolean) defaultValue);
        }else if (defaultValue instanceof Float){
            return sp.getFloat(key, (Float) defaultValue);
        }else if (defaultValue instanceof Long){
            return sp.getLong(key, (Long) defaultValue);
        }
        return null;
    }

    /**
     * 移除某个key已经对应的值
     * @param context
     * @param key
     */
    public static void remove(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);

    }

    /**
     * 清除所有数据
     * @param context
     */
    public static void clear(Context context){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 判断某个key是否存在
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);

    }



    private static class SharedPreferencesCompat{
        private static final Method sApplyMethod = findApplyMethod();


        /**
         * 通过反射查找apply方法
         * @return
         */
        private static Method findApplyMethod() {
            Class clazz = SharedPreferences.Editor.class;
            try {
                return clazz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 存储数据，如果反射找到了用apply执行，否则用commit
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor){
            if (sApplyMethod != null){
                try {
                    sApplyMethod.invoke(editor);
                } catch (IllegalArgumentException
                        | IllegalAccessException | InvocationTargetException e){
                    e.printStackTrace();
                }
            }else {
                editor.commit();
            }
        }
    }
}
