package com.mockuai.tradecenter.core.service.action.order.add.step;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.enums.EnumOrderType;
import com.mockuai.tradecenter.core.domain.TradeConfigDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.TradeConfigManager;
import com.mockuai.tradecenter.core.service.ResponseUtils;

/**
 * Created by zengzhangqiang on 5/20/16.
 */
public class LastStep extends TradeBaseStep {
    @Override
    public StepName getName() {
        return StepName.LAST_STEP;
    }

    @Override
    public TradeResponse execute() {

        OrderDTO returnOrder = (OrderDTO) this.getAttr("returnOrder");
        Integer payTimeout = (Integer) this.getAttr("payTimeout");

        //非开店礼包订单都需要填充订单超时取消时长（开店礼包不限制付款时间）
        if (returnOrder.getType() != null && returnOrder.getType().intValue() != EnumOrderType.GIFT_PACK.getCode()) {
            returnOrder.setPayTimeout(payTimeout);
        }
        return ResponseUtils.getSuccessResponse(returnOrder);
    }
}
