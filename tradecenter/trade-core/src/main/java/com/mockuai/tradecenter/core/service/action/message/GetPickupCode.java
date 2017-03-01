package com.mockuai.tradecenter.core.service.action.message;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.common.domain.message.OrderMessageDTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderStoreDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.StoreManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

/**
 * 获取提货码
 * 第一次 生成后不变
 *
 */
public class GetPickupCode implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetPickupCode.class);


	@Resource
	private OrderManager orderManager;

	@Resource
	private StoreManager storeManager;


	public TradeResponse<String> execute(RequestContext context) {

		Request request = context.getRequest();
		TradeResponse<String> response = null;

		if (request.getParam("orderMessage") == null) {
			log.error("orderMessage is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderMessage is null");
		}

		OrderMessageDTO orderMessage = (OrderMessageDTO) request.getParam("orderMessage");
		long orderId = orderMessage.getOrderId();
		long userId = orderMessage.getUserId();

		OrderDO order = null;
		try {
			order = this.orderManager.getActiveOrder(orderId, userId);
			if (order == null) {
				log.error("order is null");
				return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "order not exist");
			}
		} catch (TradeException e) {
			log.error("db error:", e);
			return ResponseUtils.getFailResponse(ResponseCode.SYS_E_DATABASE_ERROR);
		}

		if (order.getOrderStatus().intValue() < TradeConstants.Order_Status.PAID) {
			log.error("order unpaid");
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ORDER_STATUS_ERROR, "订单未支付");
		}
		
		OrderStoreDO orderStoreDO = storeManager.getOrderStore(orderId);
		if(null==orderStoreDO){
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ORDER_STATUS_ERROR, "orderStoreDO is null");
		}
		
		if(StringUtils.isNotBlank(orderStoreDO.getPickupCode())){
			response = ResponseUtils.getSuccessResponse(orderStoreDO.getPickupCode());
			return response;
		}
		
		long pickupCode = System.currentTimeMillis() % 1000000;
		String pickupCodeStr = String.format("%06d", pickupCode);
		
		Boolean updatePickupCodeResult = storeManager.updatePickupCode(orderId, pickupCodeStr);
		if(!updatePickupCodeResult){
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ORDER_STATUS_ERROR, "提货码获取失败");
		}
		
		response = ResponseUtils.getSuccessResponse(pickupCodeStr);
		
		return response;
		

	}

	@Override
	public String getName() {
		return ActionEnum.GET_PICKUP_CODE.getActionName();
	}
}
