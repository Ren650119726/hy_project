package com.mockuai.shopcenter.mop.api.action;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.shopcenter.api.BaseRequest;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.mop.api.util.MopApiUtil;

import java.util.Map;

/**
 * Created by luliang on 15/7/27.
 */
public class GetShopDetails extends BaseAction {

    public MopResponse execute(Request request) {
        String shopIdStr = (String) request.getParam("shop_id");
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getAttribute("app_key");

        // 一个用户开一个店;
        if (Strings.isNullOrEmpty(shopIdStr)) {
            return new MopResponse(ResponseCode.PARAM_E_INVALID.getCode(), "shopId is invalid");
        }
        com.mockuai.shopcenter.api.Request shopRequest = new BaseRequest();
        shopRequest.setParam("shopId", Long.valueOf(shopIdStr));
        shopRequest.setParam("userId", userId);
        shopRequest.setParam("appKey",appKey);
        shopRequest.setCommand(ActionEnum.GET_SHOP_DETAILS.getActionName());
        Response<ShopDTO> response = this.getShopService().execute(shopRequest);
        if (response.getCode() == ResponseCode.SUCCESS.getCode()) {

            Map<String,Object> data = Maps.newHashMap();
            data.put("shop",MopApiUtil.genMopShopDTO(response.getModule()));
            return new MopResponse(data);
        } else {
            return new MopResponse(response.getCode(), response.getMessage());
        }
    }

    public String getName() {
        return "/shop/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
