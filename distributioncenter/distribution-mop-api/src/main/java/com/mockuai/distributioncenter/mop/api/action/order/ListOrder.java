package com.mockuai.distributioncenter.mop.api.action.order;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.SellerOrderDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistRecordQTO;
import com.mockuai.distributioncenter.mop.api.action.BaseAction;
import com.mockuai.distributioncenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by duke on 16/5/23.
 */
public class ListOrder extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(ListOrder.class);

    public MopResponse execute(Request request) {
        Long userId = (Long) request.getAttribute("user_id");
        String statusStr = (String) request.getParam("status");
        String offsetStr = (String) request.getParam("offset");
        String countStr = (String) request.getParam("count");
        String appKey = (String) request.getParam("app_key");

        Long offset = null;
        if (offsetStr != null) {
            offset = Long.parseLong(offsetStr);
        }
        Long count = null;
        if (countStr != null) {
            count = Long.parseLong(countStr);
        }
        Integer status = null;
        if (statusStr != null) {
            status = Integer.parseInt(statusStr);
        }

        BaseRequest baseRequest = new BaseRequest();
        DistRecordQTO recordQTO = new DistRecordQTO();
        recordQTO.setOffset(offset);
        recordQTO.setCount(count == null ? null : count.intValue());
        recordQTO.setStatus(status);

        baseRequest.setParam("distRecordQTO", recordQTO);
        baseRequest.setParam("userId", userId);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.LIST_ORDER.getActionName());
        Response<List<SellerOrderDTO>> response = getDistributionService().execute(baseRequest);

        if (!response.isSuccess()) {
            log.error("list order error, errMsg: {}", response.getMessage());
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order_list", MopApiUtil.getMopOrderDTOs(response.getModule()));
        map.put("total_count", response.getTotalCount());
        return new MopResponse(map);
    }

    public String getName() {
        return "/seller/order/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
