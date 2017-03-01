package com.mockuai.virtualwealthcenter.core.manager.impl;

import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.client.ItemSkuClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.ItemManager;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class ItemManagerImpl implements ItemManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemManagerImpl.class);

    @Resource
    private ItemClient itemClient;
    @Resource
    private ItemSkuClient itemSkuClient;

    @Override
    public ItemDTO addItem(ItemDTO itemDTO, String appKey) throws VirtualWealthException {
        Response<ItemDTO> response = itemClient.addItem(itemDTO, appKey);
        if (response.isSuccess()) {
            return response.getModule();
        } else {
            LOGGER.error("add item error, errMsg: {}, errCode: {}, item: {}",
                    response.getMessage(), response.getCode(), JsonUtil.toJson(itemDTO));
            throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public Boolean deleteItem(Long itemId, Long sellerId, String appKey) throws VirtualWealthException {
        Response<Boolean> response = itemClient.deleteItem(itemId, sellerId, appKey);
        if (response.isSuccess()) {
            return response.getModule();
        } else {
            LOGGER.error("delete item error, errMsg: {}, errCode: {}, itemId: {}, sellerId: {}",
                    response.getMessage(), response.getCode(), sellerId);
            throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public Boolean updateItemSku(ItemSkuDTO itemSkuDTO, String appKey) throws VirtualWealthException {
//        Response<Boolean> response = itemSkuClient.updateItemSku(itemSkuDTO, appKey);
//        if (response.isSuccess()) {
//            return response.getModule();
//        } else {
//            LOGGER.error("update item error, errMsg: {}, errCode: {}, itemSku: {}",
//                    response.getMessage(), response.getCode(), JsonUtil.toJson(itemSkuDTO));
//            throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
//        }

        // TODO 增加充值功能时,去掉注释
        return true;
    }
}