package com.mockuai.tradecenter.core.manager;

import com.mockuai.tradecenter.core.domain.OrderViewDO;
import com.mockuai.tradecenter.core.exception.TradeException;

public interface OrderViewManager {

	public Long addOrderView(OrderViewDO viewDO)throws TradeException;
	
}
