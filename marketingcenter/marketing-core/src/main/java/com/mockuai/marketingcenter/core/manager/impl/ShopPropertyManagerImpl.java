package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ShopPropertyManager;
import com.mockuai.shopcenter.ShopPropertyClient;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.constant.PropertyConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by edgar.zr on 1/12/16.
 */
public class ShopPropertyManagerImpl implements ShopPropertyManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopPropertyManagerImpl.class);

    @Autowired
    private ShopPropertyClient shopPropertyClient;


    @Override
    public Map<String, String> getShopProperties(Long sellerId, String appKey) throws MarketingException {
        try {
            List<String> keys = new ArrayList<String>();
            keys.add(PropertyConsts.SUPPORT_DELIVERY);
            keys.add(PropertyConsts.SUPPORT_PICK_UP);
            Response<Map<String, String>> response = shopPropertyClient.getShopProperties(sellerId, keys, appKey);
            if (response.getCode() == ResponseCode.SUCCESS.getCode()) {
                if (response.getModule() == null)
                    return new HashMap<String, String>();
                return response.getModule();
            }

            LOGGER.error("error of getShopProperties, sellerId : {}, appKey : {}", sellerId, appKey);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
        } catch (MarketingException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error of getShopProperties, sellerId : {}, appKey : {}", sellerId, appKey, e);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }
    }
}