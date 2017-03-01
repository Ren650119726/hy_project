package com.mockuai.tradecenter.core.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.tradecenter.common.domain.BizLockQTO;
import com.mockuai.tradecenter.core.dao.BizLockDAO;
import com.mockuai.tradecenter.core.domain.BizLockDO;

public class BizLockDAOImpl extends SqlMapClientDaoSupport implements BizLockDAO{

	@Override
	public Long addBizLock(BizLockDO record) {
		return  (Long) this.getSqlMapClientTemplate().insert("biz_lock.add", record);
	}

	@Override
	public int deleteBizLock(BizLockQTO record) {
		return  (Integer) this.getSqlMapClientTemplate().delete("biz_lock.delete",record);
	}

	@Override
	public BizLockDO getBizLock(BizLockQTO query) {
		return (BizLockDO) this.getSqlMapClientTemplate().queryForObject("biz_lock.query",query);
	}

}
