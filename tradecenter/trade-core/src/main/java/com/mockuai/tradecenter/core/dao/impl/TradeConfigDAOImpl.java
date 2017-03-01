package com.mockuai.tradecenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.tradecenter.common.domain.BizLockQTO;
import com.mockuai.tradecenter.common.domain.TradeConfigQTO;
import com.mockuai.tradecenter.core.dao.TradeConfigDAO;
import com.mockuai.tradecenter.core.domain.BizLockDO;
import com.mockuai.tradecenter.core.domain.TradeConfigDO;

public class TradeConfigDAOImpl extends SqlMapClientDaoSupport implements TradeConfigDAO{


	@Override
	public Long addTradeConfig(TradeConfigDO record) {
		return  (Long) this.getSqlMapClientTemplate().insert("trade_config.add", record);
	}

	@Override
	public int deleteTradeConfig(TradeConfigQTO record) {
		return this.getSqlMapClientTemplate().delete("trade_config.delete", record);
	}

	@Override
	public List<TradeConfigDO> queryTradeConfig(TradeConfigQTO record) {
		return this.getSqlMapClientTemplate().queryForList("trade_config.query", record);
	}

	@Override
	public int updateTradeConfig(TradeConfigDO record) {
		return this.getSqlMapClientTemplate().update("trade_config.updateById", record);
	}

	@Override
	public TradeConfigDO getTradeConfig(String bizCode, String attrKey) {
		TradeConfigQTO query = new TradeConfigQTO();
		query.setBizCode(bizCode);
		query.setAttrKey(attrKey);
		return (TradeConfigDO) this.getSqlMapClientTemplate().queryForObject("trade_config.query", query);
	}

}
