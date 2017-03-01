package com.mockuai.itemcenter.client.impl;

import com.mockuai.itemcenter.client.MigratingClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 16/2/25.
 */
public class MigratingClientImpl implements MigratingClient {

    @Resource
    private ItemService itemService;

    public Response<Void> single2MultiShopMigrate(Long sellerId, String appKey) {
        Request request = new BaseRequest();

        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.SINGLE_2_MULTI_SHOP_MIGRATE.getActionName());
        return itemService.execute(request);
    }
}
