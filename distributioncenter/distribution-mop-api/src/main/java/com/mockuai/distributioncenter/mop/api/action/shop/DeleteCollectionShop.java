package com.mockuai.distributioncenter.mop.api.action.shop;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.mop.api.action.BaseAction;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

/**
 * Created by duke on 16/5/21.
 */
public class DeleteCollectionShop extends BaseAction {
    public MopResponse execute(Request request) {
        String idStr = (String) request.getParam("id");
        String appKey = (String) request.getParam("app_key");
        Long id = Long.parseLong(idStr);

        if (id == null) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "id is null");
        }

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("id", id);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.DELETE_COLLECTION_SHOP.getActionName());
        Response<Boolean> response = getDistributionService().execute(baseRequest);
        if (!response.isSuccess()) {
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }
        return new MopResponse(true);
    }

    public String getName() {
        return "/collection/shop/delete";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
