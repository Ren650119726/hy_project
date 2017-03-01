package com.mockuai.tradecenter.core.manager;

import com.mockuai.tradecenter.common.domain.DeliveryReturnDTO;
import com.mockuai.tradecenter.core.domain.DeliveryReturnDO;
import com.mockuai.tradecenter.core.exception.TradeException;

public interface DeliveryReturnManager {
	
	public long addDeliveryReturn(DeliveryReturnDO DeliveryReturn)throws TradeException;

	public String validateFields(DeliveryReturnDTO deliveryReturnDTO)throws TradeException;
	
}
