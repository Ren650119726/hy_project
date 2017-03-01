package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.ShopDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ShopManager;
import com.mockuai.shopcenter.ShopClient;
import com.mockuai.shopcenter.api.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by edgar.zr on 1/13/16.
 */
public class ShopManagerImpl implements ShopManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopManagerImpl.class);

    @Autowired
    private ShopClient shopClient;

    @Override
    public ShopDTO getShop(Long sellerId, String appKey) throws MarketingException {

        try {
            Response<com.mockuai.shopcenter.domain.dto.ShopDTO> response = shopClient.getShop(sellerId, appKey);
            if (response.getCode() == ResponseCode.SUCCESS.getCode()) {
                if (response.getModule() == null) {
                    LOGGER.error("error to getShop, the shop doesn't exist, sellerId : {}, appKey : {}", sellerId, appKey);
                    throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "店铺不存在");
                }
                ShopDTO shopDTO = new ShopDTO();
                BeanUtils.copyProperties(response.getModule(), shopDTO);
                return shopDTO;
            }

            LOGGER.error("error of getShop, sellerId : {}, appKey : {}", sellerId, appKey);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
        } catch (MarketingException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error of getShop, sellerId : {}, appKey : {}", sellerId, appKey, e);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }
    }
}