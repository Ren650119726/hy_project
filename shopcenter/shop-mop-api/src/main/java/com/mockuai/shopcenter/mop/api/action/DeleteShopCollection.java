package com.mockuai.shopcenter.mop.api.action;

import com.google.common.base.Strings;
import com.google.gson.reflect.TypeToken;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.shopcenter.api.BaseRequest;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.mop.api.domain.ShopUidDTO;
import com.mockuai.shopcenter.mop.api.util.JsonUtil;
import com.mockuai.shopcenter.mop.api.util.MopApiUtil;

import java.util.List;

/**
 * Created by luliang on 15/8/7.
 */
public class DeleteShopCollection extends BaseAction {

    public MopResponse execute(Request request) {

        String shopUidStr = (String)request.getParam("shop_id_list");
        String appKey = (String)request.getAttribute("app_key");


        com.mockuai.shopcenter.api.Request shopRequest = new BaseRequest();
        shopRequest.setParam("appKey",appKey);


        if(Strings.isNullOrEmpty(shopUidStr)){
            return new MopResponse(ResponseCode.PARAM_E_INVALID.getCode(), "shop_id_list is invalid");
        }

        List<Long> shopIdList;

        try{

            if(!Strings.isNullOrEmpty(shopUidStr)){

                shopIdList = JsonUtil.parseJson(shopUidStr, new TypeToken<List<Long>>() {
                }.getType());
                shopRequest.setParam("shopIdList",shopIdList);

            }

        }catch (Exception e){
            return new MopResponse(ResponseCode.PARAM_E_INVALID.getCode(), "shop_id_list is invalid");
        }

        Long userId = (Long)request.getAttribute("user_id");

        if(userId == null) {
            return new MopResponse(ResponseCode.PARAM_E_INVALID.getCode(), "userId is invalid");
        }

        shopRequest.setParam("userId", userId);
        shopRequest.setCommand(ActionEnum.DELETE_SHOP_COLLECTION.getActionName());
        Response<Boolean> response = this.getShopService().execute(shopRequest);
        if(response.getCode() == ResponseCode.SUCCESS.getCode()) {
            return new MopResponse(response.getModule());
        } else {
            return new MopResponse(response.getCode(), response.getMessage());
        }
    }

    public String getName() {
        return "/shop/collection/delete";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
