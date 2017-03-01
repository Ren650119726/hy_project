package com.mockuai.shopcenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.shopcenter.api.BaseRequest;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.domain.dto.StoreDTO;
import com.mockuai.shopcenter.mop.api.util.MopApiUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yindingyu on 15/11/4.
 */
public class GetStoreDeliveryTypeSupppotConfig extends BaseAction{

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

        final String key1 = "support_pick_up";
        final String key2 = "support_delivery";


        com.mockuai.shopcenter.api.Request shopRequest1 = new BaseRequest();

        shopRequest1.setParam("sellerId", sellerId);
        shopRequest1.setParam("appKey", appKey);
        shopRequest1.setParam("key", key1);

        shopRequest1.setCommand(ActionEnum.GET_SHOP_PROPERTY.getActionName());

        Response<String> shopResponse1 = getShopService().execute(shopRequest1);


        com.mockuai.shopcenter.api.Request shopRequest2 = new BaseRequest();

        shopRequest2.setParam("sellerId", sellerId);
        shopRequest2.setParam("appKey", appKey);
        shopRequest2.setParam("key", key2);

        shopRequest2.setCommand(ActionEnum.GET_SHOP_PROPERTY.getActionName());

        Response<String> shopResponse2 = getShopService().execute(shopRequest2);

        //将底层接口配置不存在的错误代码转化为该配置项配置结果为0
        if(shopResponse1.getCode()==ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST.getCode()){
            shopResponse1 = new ShopResponse<String>("0");
        }

        if(shopResponse2.getCode()==ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST.getCode()){
            shopResponse2 = new ShopResponse<String>("0");
        }

        if(shopResponse1.getCode()==ResponseCode.SUCCESS.getCode()){

                if(shopResponse2.getCode()==ResponseCode.SUCCESS.getCode()) {

                    Map result = new HashMap();
                    result.put(key1, shopResponse1.getModule());
                    result.put(key2, shopResponse2.getModule());

                    return new MopResponse(result);
                }else {
                    return new MopResponse(shopResponse2.getCode(),shopResponse2.getMessage());
                }
        }else{
            return new MopResponse(shopResponse1.getCode(),shopResponse1.getMessage());
        }
    }
    public String getName() {
        return "/store/config/delivery_type_support/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.NO_LIMIT;
    }
}
