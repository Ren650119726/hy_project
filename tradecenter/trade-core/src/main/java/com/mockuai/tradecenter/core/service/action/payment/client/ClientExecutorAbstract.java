package com.mockuai.tradecenter.core.service.action.payment.client;

import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.PaymentDeclareDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;

public class ClientExecutorAbstract implements ClientExecutor {

	@Override
	public PaymentUrlDTO getPaymentUrl(RequestContext context) throws TradeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void paymentDeclare(OrderDO order, String appKey,PaymentDeclareDTO paymentDeclareDTO) throws TradeException {
		// TODO Auto-generated method stub
		
	}

}
