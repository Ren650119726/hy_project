package com.mockuai.usercenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.qto.SellerUserQTO;
import com.mockuai.usercenter.core.dao.SellerUserDAO;
import com.mockuai.usercenter.core.domain.SellerUserDO;
@Service
public class SellerUserDAOImpl extends SqlMapClientDaoSupport implements SellerUserDAO{

	@Override
	public Long addSellerUser(SellerUserDO userDO) {
		return (Long)this.getSqlMapClientTemplate().insert("seller_user.add",userDO);
	}

	@Override
	public SellerUserDO getByUserIdAndSellerId(Long userId, Long sellerId) {
		SellerUserQTO query = new SellerUserQTO();
		query.setUserId(userId);
		query.setSellerId(sellerId);
		return (SellerUserDO) this.getSqlMapClientTemplate().queryForObject("seller_user.select",query);
	}

	@Override
	public int updateSellerUser(SellerUserDO userDO) {
		return this.getSqlMapClientTemplate().update("seller_user.update",userDO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SellerUserDO> querySellerUser(SellerUserQTO query) {
		return this.getSqlMapClientTemplate().queryForList("seller_user.selectByQuery",query);
	}

	@Override
	public Integer getQuerySellerUserCount(SellerUserQTO query) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("seller_user.getSelectByQueryTotalCount",query);
	}

}
