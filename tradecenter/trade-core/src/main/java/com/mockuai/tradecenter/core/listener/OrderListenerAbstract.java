package com.mockuai.tradecenter.core.listener;

import com.mockuai.tradecenter.core.domain.OrderTogetherDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderListenerAbstract implements OrderListener {

	public static final Logger log = LoggerFactory.getLogger(OrderListenerAbstract.class);
	
	@Override
	public void afterSave(OrderTogetherDTO orderTogetherDTO,String appKey) throws TradeException {
		
		
	}

}
