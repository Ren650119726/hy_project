package com.mockuai.tradecenter.core.base;

import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.PaymentDeclareDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;

/**
 * 
 * @author hzmk
 *
 */
public interface ClientExecutor {

	/**
	 * 获取支付链接
	 * @param context
	 * @return
	 */
	public PaymentUrlDTO getPaymentUrl(RequestContext context)throws TradeException;
	
	/**
	 * 支付申报
	 * @param order
	 * @param appKey
	 * @throws TradeException
	 */
	public void paymentDeclare(OrderDO order,String appKey,PaymentDeclareDTO paymentDeclareDTO)throws TradeException;
	
}
