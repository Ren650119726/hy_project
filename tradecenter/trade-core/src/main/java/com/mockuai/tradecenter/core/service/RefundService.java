package com.mockuai.tradecenter.core.service;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.core.exception.TradeException;

public interface RefundService {

	/**
	 * 确认退款
	 * 
	 * @param refundOrderItemDTO
	 * @return
	 * @throws TradeException
	 */
	public TradeResponse<?> confirmOrderItemRefund(RequestContext context) throws TradeException;
	
}
