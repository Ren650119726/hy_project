package com.mockuai.shopcenter.impl;

import com.mockuai.shopcenter.ShopPropertyClient;
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
public class ShopPropertyClientImpl implements ShopPropertyClient{

    @Resource
    private ShopService shopService;

    public Response<String> getShopProperty(Long sellerId,String key,String appKey) {

        Request request = new BaseRequest();
        request.setParam("sellerId",sellerId);
        request.setParam("key",key);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_SHOP_PROPERTY.getActionName());

        return  shopService.execute(request);
    }

    public Response<Map<String, String>> getShopProperties(Long sellerId, List<String> keys, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId",sellerId);
        request.setParam("keys",keys);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.GET_SHOP_PROPERTIES.getActionName());

        return  shopService.execute(request);
    }

    public Response<Integer> setShopProperty(Long sellerId,String key,String value,String appKey) {

        Request request = new BaseRequest();
        request.setParam("sellerId",sellerId);
        request.setParam("key",key);
        request.setParam("value",value);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.SET_SHOP_PROPERTY.getActionName());

        return  shopService.execute(request);
    }

    public Response<Integer> setShopProperties(Long sellerId, Map<String, String> props, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId",sellerId);
        request.setParam("props",props);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.SET_SHOP_PROPERTIES.getActionName());

        return  shopService.execute(request);
    }
}
