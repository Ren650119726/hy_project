package com.mockuai.tradecenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.tradecenter.common.domain.settlement.WithdrawQTO;
import com.mockuai.tradecenter.core.dao.WithdrawInfoDAO;
import com.mockuai.tradecenter.core.domain.WithdrawInfoDO;

public class WithdrawInfoDAOImpl extends SqlMapClientDaoSupport implements WithdrawInfoDAO {

	@Override
	public Long addWithdrawInfo(WithdrawInfoDO withdrawDO) {
		return (Long)this.getSqlMapClientTemplate().insert(
                "withdraw_info.add",withdrawDO);
	}

	@Override
	public int updateWithdrawInfo(WithdrawInfoDO withdrawDO) {
		return (Integer) this.getSqlMapClientTemplate().update(
                "withdraw_info.update",withdrawDO);
	}

	@Override
	public List<WithdrawInfoDO> queryWithdrawInfo(WithdrawQTO query) {
		return this.getSqlMapClientTemplate().queryForList("withdraw_info.query", query);
	}

	@Override
	public Long getQueryCount(WithdrawQTO query) {
		return (Long) this.getSqlMapClientTemplate().queryForObject("withdraw_info.getQueryCount", query);
	}

	@Override
	public WithdrawInfoDO getWithdrawInfoById(Long id) {
		return (WithdrawInfoDO) this.getSqlMapClientTemplate().queryForObject("withdraw_info.getWithdrawInfo",id);
	}

	@Override
	public Long getWithdrawSumAmount(WithdrawQTO query) {
		// TODO Auto-generated method stub
		return (Long) this.getSqlMapClientTemplate().queryForObject("withdraw_info.getWithdrawSumAmount",query);
	}

}
