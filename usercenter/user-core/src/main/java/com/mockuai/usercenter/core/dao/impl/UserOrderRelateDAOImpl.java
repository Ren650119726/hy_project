package com.mockuai.usercenter.core.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.qto.UserOrderRelateQTO;
import com.mockuai.usercenter.core.dao.UserOrderRelateDAO;
import com.mockuai.usercenter.core.domain.UserOrderRelateDO;
@Service
public class UserOrderRelateDAOImpl extends SqlMapClientDaoSupport implements UserOrderRelateDAO{

	@Override
	public Long addUserOrderRelate(UserOrderRelateDO userDO) {
		return (Long)this.getSqlMapClientTemplate().insert("user_order_relate.add",userDO);
	}

	@Override
	public UserOrderRelateDO getByUserOrderRelateQTO(UserOrderRelateQTO query) {
		return (UserOrderRelateDO) this.getSqlMapClientTemplate().queryForObject("user_order_relate.select",query);
	}

	@Override
	public int updateUserOrderRelate(UserOrderRelateDO userDO) {
		return this.getSqlMapClientTemplate().update("user_order_relate.update", userDO);
	}

	@Override
	public Integer getRecordsCountByQuery(UserOrderRelateQTO query) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("user_order_relate.selectCount",query);
	}

	@Override
	public Long getOrderTotalAmount(UserOrderRelateQTO query) {
		return (Long) this.getSqlMapClientTemplate().queryForObject("user_order_relate.getAmountByQuery",query);
	}

	
}
