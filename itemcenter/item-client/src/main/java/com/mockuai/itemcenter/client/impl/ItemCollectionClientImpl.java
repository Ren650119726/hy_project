package com.mockuai.itemcenter.client.impl;

import com.mockuai.itemcenter.client.ItemCollectionClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemCollectionDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCollectionQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/8/2.
 */
public class ItemCollectionClientImpl implements ItemCollectionClient {

    @Resource
    private ItemService itemService;

    public Response<List<ItemDTO>> queryItemCollection(ItemCollectionQTO itemCollectionQTO, String appKey) {
        Request itemReq = new BaseRequest();

        itemReq.setCommand(ActionEnum.QUERY_ITEM_COLLECTION.getActionName());
        itemReq.setParam("itemCollectionQTO", itemCollectionQTO);
        itemReq.setParam("appKey", appKey);
        return  itemService.execute(itemReq);
    }
}
