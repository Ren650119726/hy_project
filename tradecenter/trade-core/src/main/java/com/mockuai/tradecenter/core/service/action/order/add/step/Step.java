package com.mockuai.tradecenter.core.service.action.order.add.step;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.core.service.RequestContext;

/**
 * Created by zengzhangqiang on 5/19/16.
 */
public interface Step {
    public StepName getName();
    public TradeResponse execute(RequestContext requestContext);
}
