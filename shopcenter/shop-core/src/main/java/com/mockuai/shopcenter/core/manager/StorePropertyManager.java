package com.mockuai.shopcenter.core.manager;

import com.mockuai.shopcenter.core.exception.ShopException;

import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/11/3.
 */
public interface StorePropertyManager {

    String getProperty(String key, Long sellerId,Long StoreId, String bizCode) throws ShopException;

    Integer setProperty(String key, String value, Long sellerId,Long StoreId, String bizCode) throws ShopException;

    Map<String,String> getProperties(List<String> keys, Long sellerId,Long StoreId, String bizCode) throws ShopException;

    List<Long> queryStoreIdsByProperty(String key,String value,Long sellerId,String bizCode) throws ShopException;

    Integer batchDeleteProperty(Long sellerId, String key, String value, String bizCode) throws ShopException;

    Integer batchSetProperty(List<Long> storeIds, Long sellerId, String key, String value, String bizCode) throws ShopException;
}
