package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.itemcenter.client.ItemSuitClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ItemSuitManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by edgar.zr on 12/7/15.
 */
public class ItemSuitManagerImpl implements ItemSuitManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemSuitManagerImpl.class);

    @Autowired
    ItemSuitClient itemSuitClient;

    @Override
    public List<ItemDTO> querySuitsByItem(ItemQTO itemQTO, Long userId, String appKey) throws MarketingException {
        try {
            Response<List<ItemDTO>> response = itemSuitClient.querySuitsByItem(itemQTO, userId, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            LOGGER.error("error to querySuitsByItem, itemQTO : {}, userId : {}, appKey : {}, errCode : {}, errMsg : {}",
                    JsonUtil.toJson(itemQTO), userId, appKey, response.getCode(), response.getMessage());
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
        } catch (MarketingException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to querySuitsByItem, itemQTO : {}, userId : {}, appKey : {}",
                    JsonUtil.toJson(itemQTO), userId, appKey, e);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public List<ItemDTO> querySuit(ItemQTO itemQTO, Long userId, String appKey) throws MarketingException {
        try {
            Response<List<ItemDTO>> response = itemSuitClient.querySuit(itemQTO, userId, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            LOGGER.error("error to querySuit, itemQTO : {}, userId : {}, appKey : {}, errCode : {}, errMsg : {}",
                    JsonUtil.toJson(itemQTO), userId, appKey, response.getCode(), response.getMessage());
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
        } catch (MarketingException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to querySuit, itemQTO : {}, userId : {}, appKey : {}",
                    JsonUtil.toJson(itemQTO), userId, appKey, e);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public ItemDTO getSuit(Long suitId, Long userId, String appKey) throws MarketingException {
        try {
            Response<ItemDTO> response = itemSuitClient.getSuit(suitId, userId, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            LOGGER.error("error to querySuit, suitId : {}, userId : {}, appKey : {}, errCode : {}, errMsg : {}",
                    suitId, userId, appKey, response.getCode(), response.getMessage());
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
        } catch (MarketingException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to querySuit, suitId : {}, userId : {}, appKey : {}",
                    suitId, userId, appKey, e);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }
    }
}