package com.mockuai.virtualwealthcenter.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;
import com.mockuai.virtualwealthcenter.core.dao.WithdrawalsItemAppDAO;
import com.mockuai.virtualwealthcenter.core.domain.BankInfoAppDO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemAppDO;

public class WithdrawalsItemAppDAOImpl extends SqlMapClientDaoSupport implements WithdrawalsItemAppDAO{

	/**
	 * 提现申请
	 */
	@Override
	public Long addWithdrawalsItem(WithdrawalsItemAppDO withdrawalsItemAppDO) {
		 return ((Long) getSqlMapClientTemplate().insert("withdrawals_item.addWithdrawalsItem", withdrawalsItemAppDO)).longValue();
	}
	
	/**
	 * 客户管理 余额 提现流水
	 */
	@Override
	public List<WithdrawalsItemAppDO> findCustomerWithdrawalsPageList(WithdrawalsItemQTO withdrawalsItemQTO) {
		withdrawalsItemQTO.setTotalCount((Integer) getSqlMapClientTemplate().queryForObject("withdrawals_item.findCustomerWithdrawalsCount",withdrawalsItemQTO));
        return getSqlMapClientTemplate().queryForList("withdrawals_item.findCustomerWithdrawalsPageList", withdrawalsItemQTO);
	}
}
