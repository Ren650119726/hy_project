package com.mockuai.toolscenter.core.util;

/**
 * Created by zengzhangqiang on 3/4/16.
 */
public class CacheHelper {
    public static String genBizCacheKeyByBizCode(String bizCode){
        if(bizCode == null){
            return null;
        }

        return "bizCache_"+bizCode;
    }
}
