package com.mockuai.distributioncenter.mop.api.action.order;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.SellerOrderDTO;
import com.mockuai.distributioncenter.mop.api.action.BaseAction;
import com.mockuai.distributioncenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by duke on 16/5/23.
 */
public class GetOrderDetail extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(GetOrderDetail.class);

    public MopResponse execute(Request request) {
        Long userId = (Long) request.getAttribute("user_id");
        String orderIdStr = (String) request.getParam("order_id");
        String appKey = (String) request.getParam("app_key");

        if (orderIdStr == null) {
            log.error("orderId is null");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "order_id is null");
        }

        Long orderId = Long.parseLong(orderIdStr);
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("orderId", orderId);
        baseRequest.setParam("userId", userId);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_ORDER_DETAIL.getActionName());
        Response<SellerOrderDTO> response = getDistributionService().execute(baseRequest);
        if (!response.isSuccess()) {
            log.error("get order detail error, errMsg: {}", response.getMessage());
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }

        return new MopResponse(MopApiUtil.getMopOrderDetailDTO(response.getModule()));
    }

    public String getName() {
        return "/seller/order/detail/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
