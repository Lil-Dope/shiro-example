package com.cnaidun.police.util;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.UUID;

public class JsonUtil {
    public static Gson gson;

    static {
        GsonBuilder gb = new GsonBuilder();
        gb.disableHtmlEscaping();
        gb.setFieldNamingPolicy(
                FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gson = gb.create();
    }

    public static <T> T parseJson(String jsonStr, Class<T> tClass){
        return gson.fromJson(jsonStr, tClass);
    }

    public static <T> T parseJson(String jsonStr, Type typeOfT) {
        return gson.fromJson(jsonStr, typeOfT);
    }

    /**.
     * 使用Gson生成json字符串
     * @param src
     * @return
     */
    public static String toJson(Object src){
        return gson.toJson(src);
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }
}
