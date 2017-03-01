package com.mockuai.virtualwealthcenter.core.dao;

import java.util.List;

import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemAppDO;

public interface WithdrawalsItemAppDAO {

	
	Long addWithdrawalsItem(WithdrawalsItemAppDO withdrawalsItemAppDO);
	
	/**
	 * 客户管理 余额流水 提现记录
	 */
	List<WithdrawalsItemAppDO> findCustomerWithdrawalsPageList(WithdrawalsItemQTO withdrawalsItemQTO);
	
}
