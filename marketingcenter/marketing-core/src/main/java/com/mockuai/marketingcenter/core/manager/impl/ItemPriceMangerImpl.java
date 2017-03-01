package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.itemcenter.client.ItemPriceClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemPriceDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPriceQTO;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ItemManager;
import com.mockuai.marketingcenter.core.manager.ItemPriceManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by edgar.zr on 12/7/15.
 */
public class ItemPriceMangerImpl implements ItemPriceManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemPriceMangerImpl.class);

    @Autowired
    private ItemPriceClient itemPriceClient;

    @Autowired
    private ItemManager itemManager;

    @Override
    public List<ItemPriceDTO> queryItemPriceDTO(List<ItemPriceQTO> itemPriceQTOs, Long userId, String appKey) throws MarketingException {
        try {
            Response<List<ItemPriceDTO>> response = itemPriceClient.queryItemPriceDTO(itemPriceQTOs, userId, appKey);
            if (response.isSuccess()) {            	
                return response.getModule();
            }
            LOGGER.error("error to queryItemPriceDTO, itemPriceQTOs : {}, userId : {}, appKey : {}, errCode : {}, errMsg : {}",
                    JsonUtil.toJson(itemPriceQTOs), userId, appKey, response.getCode(), response.getMessage());
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        } catch (MarketingException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to queryItemPriceDTO, itemPriceQTOs : {}, userId : {}, appKey : {}",
                    JsonUtil.toJson(itemPriceQTOs), userId, appKey, e);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }
    }
}