package com.mockuai.tradecenter.core.manager;

import com.mockuai.shopcenter.domain.dto.StoreDTO;
import com.mockuai.tradecenter.core.domain.OrderStoreDO;
import com.mockuai.tradecenter.core.exception.TradeException;
public interface StoreManager {
	
	public StoreDTO getStore(Long storeId, Long sellerId,String appkey)throws TradeException;
	
	public OrderStoreDO getOrderStore(Long orderId);
	
	public Boolean updatePickupCode(Long orderId,String pickupCode);
	
	
}
