package com.mockuai.tradecenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.tradecenter.common.domain.settlement.SellerMoneyQTO;
import com.mockuai.tradecenter.core.dao.SellerMoneyDAO;
import com.mockuai.tradecenter.core.domain.SellerMoneyDO;

public class SellerMoneyDAOImpl extends SqlMapClientDaoSupport implements SellerMoneyDAO{

	@Override
	public Long addSellerMoney(SellerMoneyDO sellerMoney) {
		long id = (Long)this.getSqlMapClientTemplate().insert("seller_money.add",sellerMoney);
		return id;
	}

	@Override
	public Long updateSellerMoney(SellerMoneyDO sellerMoney) {
		return (long) this.getSqlMapClientTemplate().update("seller_money.update", sellerMoney);
	}

	@Override
	public SellerMoneyDO getSellerMoneyBySellerId(Long sellerId) {
		return (SellerMoneyDO) this.getSqlMapClientTemplate().queryForObject("seller_money.getSellerMoneyBySellerId", sellerId);
	}

	@Override
	public List<SellerMoneyDO> querySellerMoney(SellerMoneyQTO query) {
		return this.getSqlMapClientTemplate().queryForList("seller_money.querySellerMoney", query);
	}

	@Override
	public SellerMoneyDO getSellerMoney(String bizCode,Integer shopType) {
		SellerMoneyQTO query = new SellerMoneyQTO();
		query.setBizCode(bizCode);
		query.setShopType(shopType);
		return (SellerMoneyDO) this.getSqlMapClientTemplate().queryForObject("seller_money.getSellerMoney", query);
	}

}
