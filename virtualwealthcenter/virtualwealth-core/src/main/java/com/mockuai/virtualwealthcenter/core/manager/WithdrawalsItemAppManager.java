package com.mockuai.virtualwealthcenter.core.manager;

import java.util.List;

import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemAppDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

public interface WithdrawalsItemAppManager {
	Long addWithdrawalsItem(WithdrawalsItemAppDO withdrawalsItemAppDO) throws VirtualWealthException ;
	
	/**
	 * 客户管理 余额流水 提现记录
	 */
	List<WithdrawalsItemAppDO> findCustomerWithdrawalsPageList(WithdrawalsItemQTO withdrawalsItemQTO) throws VirtualWealthException ;
}
