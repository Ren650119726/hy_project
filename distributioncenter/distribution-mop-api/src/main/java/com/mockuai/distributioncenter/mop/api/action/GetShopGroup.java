package com.mockuai.distributioncenter.mop.api.action;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import static com.mockuai.distributioncenter.common.constant.ActionEnum.GET_SHOP_GROUP;

/**
 * Created by 冠生 on 2016/5/20.
 * 获得商品分组
 */
public class GetShopGroup extends BaseAction {
    public MopResponse execute(Request request) {
        String appKey = (String) request.getParam("app_key");
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(GET_SHOP_GROUP.getActionName());
        baseRequest.setParam("appKey",appKey);
        Response response = getDistributionService().execute(baseRequest);
        if(response.isSuccess()){
            return new MopResponse(response.getModule());
        }
        return new MopResponse(response.getCode(),response.getMessage());
    }

    public String getName() {
        return "/marketing/group/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
