package com.mockuai.tradecenter.core.dao;

import com.mockuai.tradecenter.core.domain.WithdrawBatchDO;
import com.mockuai.tradecenter.core.exception.TradeException;

public interface WithdrawBatchDAO {
	
	public Long addWithdrawBatch(WithdrawBatchDO withdrawBatchDO);
	
	public WithdrawBatchDO getWithdrawBatchByBatchNo(String batchno);
	
	public int updateWithdrawBatchStatus(String batchNo,String status)throws TradeException;

}
