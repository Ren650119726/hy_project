package com.mockuai.tradecenter.core.listener;

import com.mockuai.tradecenter.core.domain.OrderTogetherDTO;
import com.mockuai.tradecenter.core.exception.TradeException;

public interface OrderListener {
	
	public void afterSave(OrderTogetherDTO orderTogetherDTO,String appKey)throws TradeException;

}
