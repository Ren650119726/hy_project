package com.mockuai.shopcenter.core.manager;

import com.mockuai.shopcenter.core.exception.ShopException;

import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/11/3.
 */
public interface ShopPropertyManager {
    String getProperty(String key, Long sellerId, String bizCode) throws ShopException;

    Integer setProperty(String key, String value, Long sellerId, String bizCode) throws ShopException;

    Map<String,String> getProperties(List<String> keys, Long sellerId, String bizCode) throws ShopException;

    Integer setProperties(Map<String, String> props, Long sellerId, String bizCode) throws ShopException;
}
