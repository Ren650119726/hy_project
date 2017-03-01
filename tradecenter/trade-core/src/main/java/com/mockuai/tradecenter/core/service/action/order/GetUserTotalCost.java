package com.mockuai.tradecenter.core.service.action.order;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.*;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/8/30.
 */

@Service
public class GetUserTotalCost implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetUserTotalCost.class);

    @Resource
    OrderManager orderManager;
    @Override
    public TradeResponse execute(RequestContext context)throws TradeException {
        Request request = context.getRequest();
        TradeResponse response ;
    if (request.getParam("userId") == null) {
        log.error("userId is null ");
        return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
    }

        Long userId = (Long) request.getParam("userId");
        OrderQTO query = new OrderQTO();
        query.setUserId(userId);

       Long totalAmount = orderManager.getUserTotalCost(query);
        response = ResponseUtils.getSuccessResponse(totalAmount);
//        response = new TradeResponse(totalAmount);
        return response;
    }
    @Override
    public String getName() {
        return ActionEnum.GET_USER_TOTAL_COST.getActionName();
    }


}
