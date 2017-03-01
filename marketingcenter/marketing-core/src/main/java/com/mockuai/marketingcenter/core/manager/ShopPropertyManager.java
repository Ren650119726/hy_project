package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.Map;

/**
 * Created by edgar.zr on 1/12/16.
 */
public interface ShopPropertyManager {

    /**
     * 支持邮递的方式
     *
     * @param sellerId
     * @param appKey
     * @return
     * @throws MarketingException
     */
    Map<String, String> getShopProperties(Long sellerId, String appKey) throws MarketingException;
}