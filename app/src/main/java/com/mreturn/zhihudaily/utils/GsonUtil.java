package com.mreturn.zhihudaily.utils;

import com.google.gson.Gson;

import org.json.JSONException;

public class GsonUtil {

    //json数据转为Bean
    public static <T> T json2Bean(String json, Class<T> clazz) throws JSONException {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }
}
