package com.mockuai.itemcenter.client.impl;

import com.mockuai.itemcenter.client.ItemLabelClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemLabelDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemLabelQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/12/23.
 */
public class ItemLabelClientImpl implements ItemLabelClient{

    @Resource
    private ItemService itemService;

    public Response<ItemLabelDTO> getItemLabel(Long id, Long sellerId, String appKey) {

        Request request = new BaseRequest();
        request.setCommand(ActionEnum.GET_ITEM_LABEL.getActionName());

        request.setParam("itemLabelId", id);
        request.setParam("sellerId", sellerId);
        request.setParam("appKey",appKey);

        return itemService.execute(request);
    }

    public Response<List<ItemLabelDTO>> queryItemLabel(ItemLabelQTO itemLabelQTO, String appKey) {

        Request request = new BaseRequest();
        request.setCommand(ActionEnum.QUERY_ITEM_LABEL.getActionName());

        request.setParam("itemLabelQTO", itemLabelQTO);
        request.setParam("appKey",appKey);

        return itemService.execute(request);

    }

    public Response<Long> addItemLabel(ItemLabelDTO itemLabelDTO, String appKey) {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.ADD_ITEM_LABEL.getActionName());

        request.setParam("itemLabelDTO", itemLabelDTO);
        request.setParam("appKey",appKey);

        return itemService.execute(request);
    }

    public Response<Long> deleteItemLabel(Long id, Long sellerId, String appKey) {

        Request request = new BaseRequest();
        request.setCommand(ActionEnum.DELETE_ITEM_LABEL.getActionName());

        request.setParam("itemLabelId",id);
        request.setParam("sellerId",sellerId);
        request.setParam("appKey",appKey);

        return itemService.execute(request);
    }

    public Response<Long> updateItemLabel(ItemLabelDTO itemLabelDTO, String appKey) {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.UPDATE_ITEM_LABEL.getActionName());

        request.setParam("itemLabelDTO", itemLabelDTO);
        request.setParam("appKey",appKey);

        return itemService.execute(request);
    }
}
