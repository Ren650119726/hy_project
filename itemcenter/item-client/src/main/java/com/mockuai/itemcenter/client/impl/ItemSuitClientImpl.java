package com.mockuai.itemcenter.client.impl;

import com.mockuai.itemcenter.client.ItemSuitClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSuitDTO;
import com.mockuai.itemcenter.common.domain.dto.RItemSuitDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSuitQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/12/4.
 */
public class ItemSuitClientImpl implements ItemSuitClient{

    @Resource
    private ItemService itemService;


    public Response<List<ItemDTO>> querySuitsByItem(ItemQTO itemQTO,Long userId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemQTO",itemQTO);
        request.setParam("userId",userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_SUITS_BY_ITEM.getActionName());
        return itemService.execute(request);
    }

    public Response<List<ItemDTO>> querySuit(ItemQTO itemQTO,Long userId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemQTO",itemQTO);
        request.setParam("userId",userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_SUIT.getActionName());
        return itemService.execute(request);
    }

    public Response<ItemDTO> getSuit(Long sellerId, Long itemId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("supplierId",sellerId);
        request.setParam("id",itemId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_ITEM.getActionName());
        return itemService.execute(request);
    }

    public Response<Long> addRItemSuit(List<RItemSuitDTO> rItemSuitDTOList,String appKey) {
        Request request = new BaseRequest();
        request.setParam("rItemSuitDTOList",rItemSuitDTOList);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_R_ITEM_SUIT.getActionName());
        return itemService.execute(request);
    }

    public Response<Long> addItemSuit(ItemDTO itemDTO, List<RItemSuitDTO> rItemSuitDTOList, String appKey) {
        Request request = new BaseRequest();
        request.setParam("rItemSuitDTOList",rItemSuitDTOList);
        request.setParam("itemDTO",itemDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_ITEM_SUIT.getActionName());
        return itemService.execute(request);
    }

    public Response<Long> disableSuit(Long sellerId, Long itemId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId",sellerId);
        request.setParam("itemId",itemId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DISABLE_ITEM_SUIT.getActionName());
        return itemService.execute(request);
    }

    public Response<ItemSuitDTO> getSuitExtraInfo(Long sellerId, Long itemId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId",sellerId);
        request.setParam("itemId",itemId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_SUIT_EXTRA_INFO.getActionName());
        return itemService.execute(request);
    }

    public Response<List<ItemSuitDTO>> queryItemSuitDiscount(ItemSuitQTO itemSuitQTO,String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemSuitQTO",itemSuitQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_SUIT_DISCOUNT.getActionName());
        return itemService.execute(request);
    }
}
