package com.mockuai.itemcenter.client.impl;

import com.mockuai.itemcenter.client.ItemBuyLimitClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemBuyLimitDTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by luliang on 15/7/17.
 */
public class ItemBuyLimitClientImpl implements ItemBuyLimitClient {

    @Resource
    private ItemService itemService;

    public Response<Integer> getItemBuyLimit(Long itemId, Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemId", itemId);
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_ITEM_BUY_LIMIT.getActionName());
        return itemService.execute(request);
    }

    public Response<List<ItemBuyLimitDTO>> queryItemBuyLimit(List<Long> itemIdList, Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemIdList", itemIdList);
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_ITEM_BUY_LIMIT.getActionName());
        return itemService.execute(request);
    }
}
