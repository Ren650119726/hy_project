package com.mockuai.distributioncenter.mop.api.action.seller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * Created by duke on 16/5/19.
 */
public class CreateSellerHaiKeAction extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(CreateSellerHaiKeAction.class);

    public MopResponse execute(Request request) {
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getParam("app_key");

        if (userId == null) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "userId is null");
        }

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("userId", userId);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.CREATE_SELLER_HAIKE.getActionName());
        Response<Boolean> response = getDistributionService().execute(baseRequest);
        if (!response.isSuccess()) {
            log.error("create seller error, err: {}", response.getMessage());
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }
        return new MopResponse(response.getModule());
    }

    public String getName() {
        return "/create/seller/haike";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
