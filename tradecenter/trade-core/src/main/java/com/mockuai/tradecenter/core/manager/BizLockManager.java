package com.mockuai.tradecenter.core.manager;

import com.mockuai.tradecenter.core.domain.BizLockDO;
import com.mockuai.tradecenter.core.exception.TradeException;

public interface BizLockManager {
	
	public boolean bizLock(BizLockDO record)throws TradeException;

	
	public boolean bizUnLock(Integer type,String lockSn)throws TradeException;
}
