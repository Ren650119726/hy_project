package com.mockuai.itemcenter.client.impl;

import com.mockuai.itemcenter.client.ItemDetailTemplateClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemDetailTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemDetailTemplateQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by luliang on 15/7/21.
 */
public class ItemDetailTemplateClientImpl implements ItemDetailTemplateClient {

    @Resource
    private ItemService itemService;

    public Response<Long> addItemDetailTemplate(ItemDetailTemplateDTO itemDetailTemplateDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemDetailTemplateDTO", itemDetailTemplateDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_ITEM_DETAIL_TEMPLATE.getActionName());
        return itemService.execute(request);
    }

    public Response<ItemDetailTemplateDTO> getItemDetailTemplate(Long id, Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_ITEM_DETAIL_TEMPLATE.getActionName());
        return itemService.execute(request);
    }

    public Response<List<ItemDetailTemplateDTO>> queryItemDetailTemplate(
            ItemDetailTemplateQTO itemDetailTemplateQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemDetailTemplateQTO", itemDetailTemplateQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_ITEM_DETAIL_TEMPLATE.getActionName());
        return itemService.execute(request);
    }

    public Response<Integer> updateItemDetailTemplate(ItemDetailTemplateDTO itemDetailTemplateDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemDetailTemplateDTO", itemDetailTemplateDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_ITEM_DETAIL_TEMPLATE.getActionName());
        return itemService.execute(request);
    }

    public Response<Integer> deleteItemDetailTemplate(Long id, Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DELETE_ITEM_DETAIL_TEMPLATE.getActionName());
        return itemService.execute(request);
    }
}
