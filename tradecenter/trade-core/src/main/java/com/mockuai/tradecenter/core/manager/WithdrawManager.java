package com.mockuai.tradecenter.core.manager;

import java.util.List;

import com.mockuai.tradecenter.common.domain.settlement.NotifyWithdrawResultDTO;
import com.mockuai.tradecenter.common.domain.settlement.ProcessWithdrawDTO;
import com.mockuai.tradecenter.common.domain.settlement.WithdrawDTO;
import com.mockuai.tradecenter.common.domain.settlement.WithdrawQTO;
import com.mockuai.tradecenter.core.exception.TradeException;

/**
 * 提现
 * @author hzmk
 *
 */
public interface WithdrawManager {
	
	
	public List<WithdrawDTO> queryWithdraw(WithdrawQTO query);
	
	public Long getQueryCount(WithdrawQTO query);
	
	public Boolean processWithdraw(ProcessWithdrawDTO dto);
	
	

}
