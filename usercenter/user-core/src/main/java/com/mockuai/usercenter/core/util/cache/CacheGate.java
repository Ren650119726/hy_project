package com.mockuai.usercenter.core.util.cache;

import java.util.HashMap;

/**
 * Created by edgar.zr on 10/23/15.
 */
public class CacheGate {

    public static final int EXPIRATION = 60*30;
    private static HashMap<String, CacheValue> cache = new HashMap<String, CacheValue>();

    public static void put(String key, Object value) {
        if (key == null) {
            return;
        }
        CacheValue cacheValue;
        synchronized (cache) {
            cacheValue = new CacheValue();
            cacheValue.setValue(value);
            // 固定缓存有效时间
            cacheValue.setDeadline((int) (System.currentTimeMillis() / 1000 + EXPIRATION));
            cache.put(key, cacheValue);
        }
    }

    public static Object get(String key) {
        if (key == null) {
            return null;
        }
        CacheValue cacheValue;
        synchronized (cache) {
            cacheValue = cache.get(key);
            if (cacheValue == null) {
                return null;
            }
            if (!cacheValue.isValidate()) {
                cache.remove(key);
                return null;
            }
            return cacheValue.getValue();
        }
    }

    public static void main(String[] args) {
        String key = "theKey";
        CacheGate.put(key, "value");

        System.err.println("before : " + CacheGate.get(key));

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
        }
        System.err.println("after : " + CacheGate.get(key));
    }
}