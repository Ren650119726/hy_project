package com.mockuai.tradecenter.core.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.tradecenter.common.enums.EnumWithdrawStatus;
import com.mockuai.tradecenter.core.dao.WithdrawBatchDAO;
import com.mockuai.tradecenter.core.domain.WithdrawBatchDO;
import com.mockuai.tradecenter.core.exception.TradeException;

public class WithdrawBatchDAOImpl extends SqlMapClientDaoSupport implements WithdrawBatchDAO {

	@Override
	public Long addWithdrawBatch(WithdrawBatchDO withdrawBatchDO) {
		return (Long)this.getSqlMapClientTemplate().insert(
                "withdraw_batch.add",withdrawBatchDO);
	}

	@Override
	public WithdrawBatchDO getWithdrawBatchByBatchNo(String batchno) {
		WithdrawBatchDO query = new WithdrawBatchDO();
		query.setBatchNo(batchno);
		return (WithdrawBatchDO) this.getSqlMapClientTemplate().queryForObject("withdraw_batch.query", query);
	}

	@Override
	public int updateWithdrawBatchStatus(String batchNo, String status) throws TradeException {
		if(StringUtils.isBlank(batchNo)||StringUtils.isBlank(status))
			throw new TradeException("batchNo or status is blank");
		WithdrawBatchDO query = new WithdrawBatchDO();
		query.setBatchNo(batchNo);
		query.setStatus(EnumWithdrawStatus.getByOldCode(status).getCode());
		return this.getSqlMapClientTemplate().update("withdraw_batch.updateByBatchNo", query);
	}

}
