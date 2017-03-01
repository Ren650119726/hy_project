package com.mockuai.tradecenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.tradecenter.common.domain.CartItemServiceQTO;
import com.mockuai.tradecenter.core.dao.CartItemServiceDAO;
import com.mockuai.tradecenter.core.domain.CartItemDO;
import com.mockuai.tradecenter.core.domain.CartItemServiceDO;


public class CartItemServiceDAOImpl extends SqlMapClientDaoSupport implements CartItemServiceDAO {

	@Override
	public Long addCartItemService(CartItemServiceDO record) {
		return (Long) this.getSqlMapClientTemplate().insert("cart_item_service.add", record);
	}

	@Override
	public List<CartItemServiceDO> queryCartServiceList(CartItemServiceQTO query) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList("cart_item_service.query", query);
	}

	@Override
	public Long deleteByCartId(CartItemServiceQTO query) {
		return (long) this.getSqlMapClientTemplate().delete("cart_item_service.delete", query);
	}


}