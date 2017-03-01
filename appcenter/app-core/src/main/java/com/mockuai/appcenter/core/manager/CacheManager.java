package com.mockuai.appcenter.core.manager;

/**
 * Created by zengzhangqiang on 6/12/15.
 */
public interface CacheManager {
    public void set(String key, int expired, Object obj);

    public Object get(String key);

    public Object getAndTouch(String key, int expired);

    public void delete(String key);
}
