package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.core.exception.ItemException;

/**
 * Created by yindingyu on 15/11/13.
 */
public interface ShopPropertyManager{

        String getShopConfig(String key, Long sellerId, String appKey) throws ItemException;

}
