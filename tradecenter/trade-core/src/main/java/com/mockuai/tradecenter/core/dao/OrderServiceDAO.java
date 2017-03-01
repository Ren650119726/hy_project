package com.mockuai.tradecenter.core.dao;

import java.util.List;

import com.mockuai.tradecenter.common.domain.OrderServiceQTO;
import com.mockuai.tradecenter.core.domain.OrderServiceDO;

public interface OrderServiceDAO {

	public Long addOrderService(OrderServiceDO record);
	
	public List<OrderServiceDO> queryOrderService(OrderServiceQTO query);
	
	
}
