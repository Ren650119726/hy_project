package com.mockuai.tradecenter.core.dao;

import java.util.List;

import com.mockuai.tradecenter.common.domain.CartItemServiceQTO;
import com.mockuai.tradecenter.common.domain.OrderServiceQTO;
import com.mockuai.tradecenter.core.domain.CartItemServiceDO;
import com.mockuai.tradecenter.core.domain.OrderServiceDO;

public interface CartItemServiceDAO {

	public Long addCartItemService(CartItemServiceDO record);
	
	public List<CartItemServiceDO> queryCartServiceList(CartItemServiceQTO query);
	
	public Long deleteByCartId(CartItemServiceQTO query);
	
	
}
