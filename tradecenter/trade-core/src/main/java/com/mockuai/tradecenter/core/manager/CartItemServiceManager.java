package com.mockuai.tradecenter.core.manager;

import java.util.List;

import com.mockuai.tradecenter.core.domain.CartItemServiceDO;
import com.mockuai.tradecenter.core.domain.OrderServiceDO;

/**
 *
 */
public interface CartItemServiceManager {
	
	public List<CartItemServiceDO> queryUserCartItemServiceList(Long cartId);

}
