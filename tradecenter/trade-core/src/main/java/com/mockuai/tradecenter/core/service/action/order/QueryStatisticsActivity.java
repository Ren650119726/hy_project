package com.mockuai.tradecenter.core.service.action.order; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.StatisticsActivityInfoDTO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderDiscountInfoManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hy on 2016/7/21.
 */
@Service
public class QueryStatisticsActivity implements Action {
    @Autowired
    private OrderDiscountInfoManager orderDiscountInfoManager;

    @Override
    public TradeResponse execute(RequestContext context) throws TradeException {
        Request request =  context.getRequest();
        long activityId = (long) request.getParam("activityId");
        OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
        orderDiscountInfoDO.setMarketActivityId(activityId);
        StatisticsActivityInfoDTO statisticsActivityInfoDTO =
                orderDiscountInfoManager.queryActivityOrder(orderDiscountInfoDO);
        return   ResponseUtils.getSuccessResponse(statisticsActivityInfoDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_ACTIVITY_STATISTICS.getActionName();
    }
}
