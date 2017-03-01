package com.mockuai.tradecenter.core.manager.impl;

import com.mockuai.tradecenter.core.manager.CacheManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zengzhangqiang on 7/2/15.
 */
public class CacheManagerLocal implements CacheManager{
    private Map<String,CacheItemWrapper> cacheMap = new HashMap<String, CacheItemWrapper>();

    public void set(String key, int expired, Object obj) {
        CacheItemWrapper cacheItemWrapper = new CacheItemWrapper();
        cacheItemWrapper.setExpired(expired);
        cacheItemWrapper.setObj(obj);
        cacheItemWrapper.setStoreTime(System.currentTimeMillis());
        cacheMap.put(key, cacheItemWrapper);
//        return;
    }

    public Object get(String key) {
        if(cacheMap.containsKey(key) == false){
            return null;
        }

        CacheItemWrapper cacheItemWrapper = cacheMap.get(key);
        if((cacheItemWrapper.getStoreTime()+cacheItemWrapper.getExpired()*1000L) > System.currentTimeMillis()){
            cacheMap.remove(key);
            return null;
        }else{
            return cacheItemWrapper.getObj();
        }
//        return null;
    }

    public Object getAndTouch(String key, int expired) {
        return null;
    }

    private class CacheItemWrapper{
        private long storeTime;
        private int expired;
        private Object obj;

        public long getStoreTime() {
            return storeTime;
        }

        public void setStoreTime(long storeTime) {
            this.storeTime = storeTime;
        }

        public int getExpired() {
            return expired;
        }

        public void setExpired(int expired) {
            this.expired = expired;
        }

        public Object getObj() {
            return obj;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }
    }
}
