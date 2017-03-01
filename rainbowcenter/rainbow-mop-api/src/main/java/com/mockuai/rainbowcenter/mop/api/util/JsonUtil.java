package com.mockuai.rainbowcenter.mop.api.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by duke on 15/10/28.
 */
public class JsonUtil {
    public static Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    public static <T> T parseJson(String jsonStr, Class<T> tClass) {
        return gson.fromJson(jsonStr, tClass);
    }

    public static <T> T parseJson(String jsonStr, Type typeOfT) {
        return gson.fromJson(jsonStr, typeOfT);
    }

    public static String toJson(Object src) {
        return gson.toJson(src);
    }
}
