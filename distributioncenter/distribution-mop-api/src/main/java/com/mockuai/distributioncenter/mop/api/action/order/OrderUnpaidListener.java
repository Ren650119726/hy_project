package com.mockuai.distributioncenter.mop.api.action.order;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.mop.api.action.BaseAction;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

/**
 * Created by duke on 16/5/23.
 */
public class OrderUnpaidListener extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(OrderUnpaidListener.class);

    
    public MopResponse execute(Request request) {
    	
    	Long orderId = (Long) request.getParam("id");
        
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("orderId", orderId);
        baseRequest.setCommand(ActionEnum.FENYONG_ORDER_UNPAID.getActionName());
        Response<Boolean> response = getDistributionService().execute(baseRequest);
        if (!response.isSuccess()) {
            log.error("do distribution error, errMsg: {}", response.getMessage());
        }
        log.info("do distribution successful");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("boolean", response.isSuccess());
        return new MopResponse(map);
    }
    
    
    public String getName() {
        return "/fenyong/order/Unpaid";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
