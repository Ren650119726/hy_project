package com.mockuai.tradecenter.core.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.tradecenter.common.domain.BizLockQTO;
import com.mockuai.tradecenter.core.dao.BizLockDAO;
import com.mockuai.tradecenter.core.domain.BizLockDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.BaseService;
import com.mockuai.tradecenter.core.manager.BizLockManager;

public class BizLockManagerImpl extends BaseService implements BizLockManager {

	@Autowired
	BizLockDAO  bizLockDAO;
	
	@Override
	public boolean bizLock(BizLockDO record) throws TradeException {
		Long result = bizLockDAO.addBizLock(record);
		if(result>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean bizUnLock(Integer type,String lockSn) throws TradeException {
		BizLockQTO query = new BizLockQTO();
		query.setType(type);
		query.setLockSn(lockSn);
		int deleteResult = bizLockDAO.deleteBizLock(query);
		if(deleteResult>0)
			return true;
		return false;
	}

}
