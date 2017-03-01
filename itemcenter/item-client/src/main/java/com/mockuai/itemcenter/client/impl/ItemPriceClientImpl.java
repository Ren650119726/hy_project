package com.mockuai.itemcenter.client.impl;

import com.mockuai.itemcenter.client.ItemPriceClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemPriceDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPriceQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/12/4.
 */
public class ItemPriceClientImpl implements ItemPriceClient{

    @Resource
    private ItemService itemService;


    public Response<List<ItemPriceDTO>> queryItemPriceDTO(List<ItemPriceQTO> itemPriceQTOList,Long userId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId",userId);
        request.setParam("itemPriceQTOList", itemPriceQTOList);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_ITEMS_PRICE.getActionName());
        return itemService.execute(request);
    }
}
