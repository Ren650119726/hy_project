package com.mockuai.tradecenter.core.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.tradecenter.common.domain.CartItemServiceQTO;
import com.mockuai.tradecenter.common.domain.OrderServiceQTO;
import com.mockuai.tradecenter.core.dao.CartItemServiceDAO;
import com.mockuai.tradecenter.core.domain.CartItemServiceDO;
import com.mockuai.tradecenter.core.domain.OrderServiceDO;
import com.mockuai.tradecenter.core.manager.BaseService;
import com.mockuai.tradecenter.core.manager.CartItemServiceManager;

public class CartItemServiceManagerImpl extends BaseService implements CartItemServiceManager{

	@Autowired
	CartItemServiceDAO cartItemServiceDAO;
	

	@Override
	public List<CartItemServiceDO> queryUserCartItemServiceList(Long cartId) {
		// TODO Auto-generated method stub
		CartItemServiceQTO query = new CartItemServiceQTO();
		query.setCartId(cartId);
		return cartItemServiceDAO.queryCartServiceList(query);
	}

}
