package com.mockuai.itemcenter.client.impl;

import com.mockuai.itemcenter.client.SkuPropertyClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/5/18.
 */
public class SkuPropertyClientImpl implements SkuPropertyClient {

    @Resource
    private ItemService itemService;

    public Response<List<SkuPropertyDTO>> querySkuProperty(SkuPropertyQTO qto, String appKey) {
        Request request = new BaseRequest();
        request.setParam("skuPropertyQTO", qto);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.QUERY_SKU_PROPERTY.getActionName());
        return itemService.execute(request);
    }
}
