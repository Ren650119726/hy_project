package com.mockuai.tradecenter.core.service.action.order;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.OrderStatisticDTO;
import com.mockuai.tradecenter.core.dao.RefundDAO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

public class GetOrderStatistic
        implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetOrderStatistic.class);

    @Resource
    private OrderManager orderManager;
    
    @Autowired
    private RefundDAO refundDAO;

    public TradeResponse<OrderStatisticDTO> execute(RequestContext context) throws TradeException {
        Request request = context.getRequest();
        TradeResponse response = null;

        if (request.getParam("userId") == null) {
            log.error("userId is null ");
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
        }
        
        String bizCode = (String) context.get("bizCode");

        long userId = ((Long) request.getParam("userId")).longValue();
        try {
            OrderQTO orderQTO = new OrderQTO();
            orderQTO.setBizCode(bizCode);
            orderQTO.setUserId(Long.valueOf(userId));
            /*排除父订单*/
            orderQTO.setParentMark(0);

            orderQTO.setOrderStatus(Integer.valueOf(10));
            int initialOrderNum = this.orderManager.getTotalCount(orderQTO);

            orderQTO.setOrderStatus(Integer.valueOf(30));
            int payedOrderNum = this.orderManager.getTotalCount(orderQTO);
            
            orderQTO.setOrderStatus(Integer.valueOf(35));
            int unDeliveredOrderNum = this.orderManager.getTotalCount(orderQTO);

            orderQTO.setOrderStatus(Integer.valueOf(40));
            int deliveredOrderNum = this.orderManager.getTotalCount(orderQTO);

            orderQTO.setOrderStatus(Integer.valueOf(50));
            int receiptedOrderNum = this.orderManager.getTotalCount(orderQTO);

            OrderStatisticDTO orderStatisticDTO = new OrderStatisticDTO();
            orderStatisticDTO.setInitialOrderNum(Integer.valueOf(initialOrderNum));
            orderStatisticDTO.setPayedOrderNum(Integer.valueOf(payedOrderNum+unDeliveredOrderNum));
            orderStatisticDTO.setDeliveredOrderNum(Integer.valueOf(deliveredOrderNum));
            orderStatisticDTO.setReceiptedOrderNum(Integer.valueOf(receiptedOrderNum));
            
            //增加退款中的订单数
            Long refundingOrderNum = refundDAO.getRefundingOrderNum(userId, bizCode);
            orderStatisticDTO.setRefundingOrderNum(refundingOrderNum.intValue());
            return ResponseUtils.getSuccessResponse(orderStatisticDTO);
        } catch (TradeException e) {
            log.error("db error: ", e);
            response = ResponseUtils.getFailResponse(e.getResponseCode());
        }
        return response;
    }

    public String getName() {
        return ActionEnum.GET_ORDER_STATISTIC.getActionName();
    }
}

/* Location:           /work/tmp/tradecenter/WEB-INF/classes/
 * Qualified Name:     com.mockuai.tradecenter.core.service.action.order.GetOrderStatistic
 * JD-Core Version:    0.6.2
 */