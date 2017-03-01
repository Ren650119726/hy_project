package com.mockuai.tradecenter.core.dao;

import java.util.List;

import com.mockuai.tradecenter.common.domain.settlement.WithdrawQTO;
import com.mockuai.tradecenter.core.domain.WithdrawInfoDO;

/**
 * 提现申请DAO
 * @author hzmk
 *
 */
public interface WithdrawInfoDAO {
	
	public Long addWithdrawInfo(WithdrawInfoDO withdrawDO);
	
	public int updateWithdrawInfo(WithdrawInfoDO withdrawDO);
	
	public List<WithdrawInfoDO> queryWithdrawInfo(WithdrawQTO query);
	
	public Long getQueryCount(WithdrawQTO query);
	
	public WithdrawInfoDO getWithdrawInfoById(Long id);
	
	public Long getWithdrawSumAmount(WithdrawQTO query);

}
