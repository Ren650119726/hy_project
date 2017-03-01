package com.mockuai.tradecenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.tradecenter.common.domain.settlement.SellerTransLogQTO;
import com.mockuai.tradecenter.core.dao.SellerTransLogDAO;
import com.mockuai.tradecenter.core.domain.SellerTransLogDO;

public class SellerTransLogDAOImpl extends SqlMapClientDaoSupport implements SellerTransLogDAO{

	@Override
	public Long addSellerTransLog(SellerTransLogDO transLog) {
		long id = (Long)this.getSqlMapClientTemplate().insert("seller_trans_log.add",transLog);
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SellerTransLogDO> querySellerTransLog(SellerTransLogQTO query) {
		if(null==query.getOffset()) 
			query.setOffset(0);
		if(null==query.getCount()) 
			query.setCount(20);
		return this.getSqlMapClientTemplate().queryForList("seller_trans_log.query", query);
	}

	@Override
	public Long getQueryCount(SellerTransLogQTO query) {
		return (Long) this.getSqlMapClientTemplate().queryForObject("seller_trans_log.getQueryCount",query);
	}

	@Override
	public SellerTransLogDO getTranslogByWithdrawId(Long withdrawId) {
		SellerTransLogQTO query = new SellerTransLogQTO();
		query.setWithdrawId(withdrawId);
		query.setOffset(0);
		query.setCount(1);
		return (SellerTransLogDO) this.getSqlMapClientTemplate().queryForObject("seller_trans_log.query", query);
	}

	@Override
	public void updateById(SellerTransLogDO translogDO) {
		this.getSqlMapClientTemplate().update("seller_trans_log.updateById", translogDO);
	}

	@Override
	public SellerTransLogDO getTransLogByOrderIdAndType(String osn,String type) {
		SellerTransLogQTO query = new SellerTransLogQTO();
		query.setOrderSn(osn);
		query.setType(type);
		query.setOffset(0);
		query.setCount(1);
		return (SellerTransLogDO) this.getSqlMapClientTemplate().queryForObject("seller_trans_log.query",query);
	}

	@Override
	public SellerTransLogDO getTransLogByOrderSnAndSkuId(String orderSn, Long skuId) {
		SellerTransLogQTO query = new SellerTransLogQTO();
		query.setOrderSn(orderSn);
		query.setItemSkuId(skuId);
		query.setOffset(0);
		query.setCount(1);
		return (SellerTransLogDO) this.getSqlMapClientTemplate().queryForObject("seller_trans_log.query",query);
	}

	@Override
	public SellerTransLogDO getSingleTransLog(String orderSN, String type, int shopType) {
		SellerTransLogQTO query = new SellerTransLogQTO();
		query.setOrderSn(orderSN);
		query.setType(type);
		query.setOffset(0);
		query.setCount(1);
		query.setShopType(shopType);
		return (SellerTransLogDO) this.getSqlMapClientTemplate().queryForObject("seller_trans_log.query",query);
	}

}
