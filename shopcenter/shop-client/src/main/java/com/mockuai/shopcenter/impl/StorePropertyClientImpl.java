package com.mockuai.shopcenter.impl;

import com.mockuai.shopcenter.ShopPropertyClient;
import com.mockuai.shopcenter.StorePropertyClient;
import com.mockuai.shopcenter.api.BaseRequest;
import com.mockuai.shopcenter.api.Request;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.api.ShopService;
import com.mockuai.shopcenter.constant.ActionEnum;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/11/4.
 */
public class StorePropertyClientImpl implements StorePropertyClient {

    @Resource
    private ShopService shopService;


    public Response<List<Long>> queryStoreIdsByProperty(Long sellerId, String key, String value, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId",sellerId);
        request.setParam("key",key);
        request.setParam("value",value);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_STORE_IDS_BY_PROPERTY.getActionName());

        return  shopService.execute(request);
    }

    public Response<Integer> batchResetStoreProperty(List<Long> storeIds, Long sellerId, String key, String value, String appKey) {

        Request request = new BaseRequest();
        request.setParam("storeIds",storeIds);
        request.setParam("sellerId",sellerId);
        request.setParam("key",key);
        request.setParam("value",value);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.BATCH_RESET_STORE_PROPERTY.getActionName());

        return  shopService.execute(request);
    }
}
