package com.mockuai.tradecenter.core.service.action.order.add.step;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;

/**
 * 订单支付方式校验
 * Created by zengzhangqiang on 5/19/16.
 */
public class CheckOrderPaymentStep extends TradeBaseStep {
    @Override
    public StepName getName() {
        return StepName.CHECK_ORDER_PAYMENT_STEP;
    }

    @Override
    public TradeResponse execute() {
        OrderDTO orderDTO = (OrderDTO) this.getParam("orderDTO");
        int payType = orderDTO.getPaymentId();
        if (TradeConstants.PaymentType.isValidPayType(payType) == false) {
            logger.error("payment is not valid: " + orderDTO.getPaymentId());
            return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_PAYMENT_TYPE_ERROR, "payment type is invalid");
        }

        return ResponseUtils.getSuccessResponse(null);
    }
}
