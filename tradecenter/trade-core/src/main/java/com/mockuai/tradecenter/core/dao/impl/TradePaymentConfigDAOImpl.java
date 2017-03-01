package com.mockuai.tradecenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.tradecenter.common.domain.TradePaymentConfigQTO;
import com.mockuai.tradecenter.core.dao.TradePaymentConfigDAO;
import com.mockuai.tradecenter.core.domain.TradePaymentConfigDO;

public class TradePaymentConfigDAOImpl extends SqlMapClientDaoSupport implements TradePaymentConfigDAO{

	@Override
	public List<TradePaymentConfigDO> queryTradePaymentConfig(
			TradePaymentConfigQTO record) {
		
		return this.getSqlMapClientTemplate().queryForList("trade_payment_config.query", record);
	}


}
