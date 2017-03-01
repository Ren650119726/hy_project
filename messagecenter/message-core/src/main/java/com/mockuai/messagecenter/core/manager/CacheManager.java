package com.mockuai.messagecenter.core.manager;

/**
 * Created by duke on 15/9/28.
 */
public interface CacheManager {
    void set(String key, int expired, Object obj);

    Object get(String key);

    public Object getAndTouch(String key, int expired);

    void remove(String key);
}
