package com.mockuai.itemcenter.client.impl;

import com.mockuai.itemcenter.client.ItemDescTmplClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemDescTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemDescTmplQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/3/8.
 */
public class ItemDescTmplClientImpl implements ItemDescTmplClient {

    @Resource
    private ItemService itemService;

    public Response<ItemDescTmplDTO> getItemDescTmpl(Long tmplId, Long sellerId, String appKey) {
        Request request = new BaseRequest();

        request.setParam("itemDescTmplId", tmplId);
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_ITEM_DESC_TMPL.getActionName());
        return itemService.execute(request);
    }

    public Response<Long> deleteItemDescTmpl(Long tmplId, Long sellerId, String appKey) {
        Request request = new BaseRequest();

        request.setParam("itemDescTmplId", tmplId);
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DELETE_ITEM_DESC_TMPL.getActionName());
        return itemService.execute(request);
    }

    public Response<Long> addItemDescTmpl(ItemDescTmplDTO itemDescTmplDTO, String appKey) {
        Request request = new BaseRequest();

        request.setParam("itemDescTmplDTO", itemDescTmplDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_ITEM_DESC_TMPL.getActionName());
        return itemService.execute(request);
    }

    public Response<Long> updateItemDescTmpl(ItemDescTmplDTO itemDescTmplDTO, String appKey) {

        Request request = new BaseRequest();

        request.setParam("itemDescTmplDTO", itemDescTmplDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_ITEM_DESC_TMPL.getActionName());
        return itemService.execute(request);
    }

    public Response<List<ItemDescTmplDTO>> queryItemDescTmpl(ItemDescTmplQTO itemDescTmplQTO, String appKey) {
        Request request = new BaseRequest();

        request.setParam("itemDescTmplQTO", itemDescTmplQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_ITEM_DESC_TMPL.getActionName());
        return itemService.execute(request);
    }
}
