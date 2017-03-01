package com.mockuai.shopcenter.mop.api.action;

import com.google.common.collect.Lists;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.shopcenter.api.BaseRequest;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.PropertyConsts;
import com.mockuai.shopcenter.constant.ResponseCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/11/4.
 */
public class GetStoreRepairConfig extends BaseAction{

    public MopResponse execute(Request request) {

        String sellerIdStr = (String) request.getParam("seller_id");
        String appKey = (String) request.getParam("app_key");

        if(sellerIdStr == null){
            return new MopResponse(ResponseCode.PARAM_E_MISSING.getCode(),"sellerId不能为空");
        }

        long sellerId = 0L;

        try{
            sellerId = Long.parseLong(sellerIdStr);
        }catch (Exception e){
            return new MopResponse(ResponseCode.PARAM_E_INVALID.getCode(),"seller_id格式错误");
        }

        final String key1 = PropertyConsts.REPAIR_RECEIVER;
        final String key2 = PropertyConsts.REPAIR_MOBILE;
        final String key3 = PropertyConsts.REPAIR_ADDRESS;

        List<String> keys = Lists.newArrayList(key1,key2,key3);

        com.mockuai.shopcenter.api.Request shopRequest = new BaseRequest();

        shopRequest.setParam("sellerId", sellerId);
        shopRequest.setParam("appKey", appKey);
        shopRequest.setParam("keys", keys);

        shopRequest.setCommand(ActionEnum.GET_SHOP_PROPERTIES.getActionName());

        Response<Map<String,String>> shopResponse = getShopService().execute(shopRequest);

        if(shopResponse.getCode()==ResponseCode.SUCCESS.getCode()) {
            Map result = new HashMap<String,Object>();
            result.put("store", shopResponse.getModule());
            return new MopResponse(result);
        }else{
            return new MopResponse(shopResponse.getCode(),shopResponse.getMessage());
        }


    }
    public String getName() {
        return "/store/config/repair/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.NO_LIMIT;
    }
}
