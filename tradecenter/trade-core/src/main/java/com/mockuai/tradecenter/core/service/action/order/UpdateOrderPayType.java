package com.mockuai.tradecenter.core.service.action.order;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.OrderPaymentManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by zengzhangqiang on 6/4/15.
 */
public class UpdateOrderPayType implements Action {
    private static final Logger log = LoggerFactory.getLogger(UpdateOrderMemo.class);

    @Resource
    private OrderManager orderManager;

    @Resource
    private OrderPaymentManager orderPaymentManager;

    @Resource
    private TransactionTemplate transactionTemplate;

    public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
        Request request = context.getRequest();
        TradeResponse<Boolean> response = null;
        if (request.getParam("orderId") == null) {
            log.error("orderId is null ");
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderId is null");
        } else if (request.getParam("userId") == null) {
            log.error("userId is null");
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
        } else if (request.getParam("paymentId") == null) {
            log.error("paymentId is null");
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "paymentId is null");
        }

        final Long orderId = (Long) request.getParam("orderId");
        final Long userId = (Long) request.getParam("userId");
        final Integer paymentId = (Integer) request.getParam("paymentId");
        
        log.info(" UpdateOrderPayType orderId:{},userId:{},paymentId:{} ",orderId,userId,paymentId);

        final OrderDO order = this.orderManager.getActiveOrder(orderId, userId);
        if (order == null) {
            log.error("order doesn't exist orderId:" + orderId + " userId:" + userId);
            return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "order doesn't exist");
        }

        TradeResponse transResult = transactionTemplate.execute(new TransactionCallback<TradeResponse>() {
            public TradeResponse doInTransaction(TransactionStatus transactionStatus) {
                try {
                    //同步更新卖家和买家订单表的支付方式
                    orderManager.updateOrderPayType(order, paymentId);

                    return new TradeResponse(Boolean.TRUE);
                }catch(TradeException e){
                    //回滚事务
                    transactionStatus.setRollbackOnly();
                    log.error("", e);
                    return ResponseUtils.getFailResponse(e.getResponseCode());
                }catch(Exception e){
                    //回滚事务
                    transactionStatus.setRollbackOnly();
                    log.error("", e);
                    return ResponseUtils.getFailResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
                }
            }
        });

        return transResult;
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_ORDER_PAY_TYPE.getActionName();
    }
}
