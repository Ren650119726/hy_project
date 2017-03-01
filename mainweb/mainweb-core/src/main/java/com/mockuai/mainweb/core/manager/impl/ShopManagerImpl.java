package com.mockuai.mainweb.core.manager.impl;

import com.mockuai.mainweb.core.manager.ShopManager;
import com.mockuai.shopcenter.ShopClient;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ShopManagerImpl implements ShopManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopManagerImpl.class);

    @Resource
    private ShopClient shopClient;

    @Override
    public ShopItemGroupDTO getShopItemGroup(Long sellerId, Long groupId, String needItems, String appKey) {
        try {
            Response<ShopItemGroupDTO> response = shopClient.getShopItemGroup(sellerId, groupId, needItems, appKey);
            if (response.getCode() == ResponseCode.SUCCESS.getCode()) {
                return response.getModule();
            }
            LOGGER.error("error to getShopItemGroup, sellerId : {}, groupId : {}, needItems : {}, appKey : {}, resCode : {}, resMsg : {}",
                    sellerId, groupId, needItems, appKey, response.getCode(), response.getMessage());
        } catch (Exception e) {
            LOGGER.error("error to getShopItemGroup, sellerId : {}, groupId : {}, needItems : {}, appKey : {}",
                    sellerId, groupId, needItems, appKey, e);
        }
        return null;
    }
}
