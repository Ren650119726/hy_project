package com.mockuai.tradecenter.mop.api.util;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;


public class JsonUtil {
    public static Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

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

/* Location:           /work/tmp/trade-mop-api-0.0.1-20150519.033139-22.jar
 * Qualified Name:     com.mockuai.tradecenter.mop.api.util.JsonUtil
 * JD-Core Version:    0.6.2
 */