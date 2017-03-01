package com.mockuai.tradecenter.core.service.action.order;

import javax.annotation.Resource;

import com.mockuai.tradecenter.core.manager.MsgQueueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

/**
 * 修改未支付订单为已支付
 */
public class UpdateOrder2Paid implements Action {
	private static final Logger log = LoggerFactory.getLogger(UpdateOrder2Paid.class);

	@Resource
	private OrderManager orderManager;
	
    @Resource
    private MsgQueueManager msgQueueManager;

	public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<Boolean> response = null;
		if (request.getParam("orderId") == null) {
			log.error("orderId is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderId is null");

		} else if (request.getParam("userId") == null) {
			log.error("userId is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
		}

		long orderId = (Long) request.getParam("orderId");
		long userId = (Long) request.getParam("userId");

		OrderDO order = this.orderManager.getActiveOrder(orderId, userId);
		if (order == null) {
			log.error("order doesn't exist orderId:" + orderId + " userId:" + userId);
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "order doesn't exist");
		}

		if (order.getOrderStatus() != TradeConstants.Order_Status.UNPAID) {
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ORDER_STATUS_ERROR, "订单状态错误");
		}

		int result = 0;
		try {

			result = orderManager.orderPaySuccess(orderId,null, userId);

		} catch (TradeException e) {
			log.error("db error : " + e);
			throw new TradeException(e.getResponseCode());
		}

		if (result > 0) {
			response = ResponseUtils.getSuccessResponse(true);
			msgQueueManager.sendPaySuccessMsg(order);
		} else {
			log.error("updated failed ");
			response = ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST);
		}
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.UPDATE_ORDER_2_PAID.getActionName();
	}
}
