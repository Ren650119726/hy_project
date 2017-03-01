package com.mockuai.tradecenter.core.dao;

import com.mockuai.tradecenter.common.domain.BizLockQTO;
import com.mockuai.tradecenter.core.domain.BizLockDO;

public interface BizLockDAO {

	public Long addBizLock(BizLockDO record);

	public int deleteBizLock(BizLockQTO record);

	public BizLockDO getBizLock(BizLockQTO query);
}
