package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.StoreDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.StoreManager;
import com.mockuai.shopcenter.StoreClient;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.constant.PropertyConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by edgar.zr on 1/14/16.
 */
public class StoreManagerImpl implements StoreManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreManagerImpl.class);

    @Autowired
    private StoreClient storeClient;

    @Override
    public StoreDTO getStore(Long sellerId, Long userId, Long consigneeId, String appKey) throws MarketingException {
        try {
            Response<com.mockuai.shopcenter.domain.dto.StoreDTO> response =
                    storeClient.getStoreByAddress(sellerId, userId, consigneeId, PropertyConsts.SUPPORT_DELIVERY, appKey);
            if (response.getCode() == ResponseCode.SUCCESS.getCode()) {
                if (response.getModule() == null)
                    return null;
                StoreDTO storeDTO = new StoreDTO();
                BeanUtils.copyProperties(response.getModule(), storeDTO);
                return storeDTO;
            }

            LOGGER.error("error of getStore, consigneeId : {}, sellerId : {}, appKey : {}, errCode : {}, errMsg: {}",
                    consigneeId, sellerId, appKey, response.getCode(), response.getMessage());
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
        } catch (MarketingException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error of getStore, consigneeId : {}, sellerId : {}, appKey : {}", consigneeId, sellerId, appKey, e);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }
    }
}