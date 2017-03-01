package com.mockuai.distributioncenter.mop.api.action.seller;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.SellerCenterDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.mop.api.action.BaseAction;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by duke on 16/5/19.
 */
public class SellerCenterAction extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(SellerCenterAction.class);

    public MopResponse execute(Request request) {
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getParam("app_key");

        if (userId == null) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "userId is null");
        }

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("userId", userId);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_SELLER_CENTER.getActionName());
        Response<SellerCenterDTO> response = getDistributionService().execute(baseRequest);
        if (!response.isSuccess()) {
            log.error("get seller error, err: {}", response.getMessage());
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }
        int isSeller = 0;
        if (response.getModule() != null) {
            isSeller = 1;
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("is_seller", isSeller);
        data.put("seller_info", response.getModule());
        return new MopResponse(data);
    }

    public String getName() {
        return "/seller/center/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
