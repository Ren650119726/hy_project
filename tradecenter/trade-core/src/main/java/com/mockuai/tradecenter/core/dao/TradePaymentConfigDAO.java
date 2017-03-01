package com.mockuai.tradecenter.core.dao;

import java.util.List;

import com.mockuai.tradecenter.common.domain.TradePaymentConfigQTO;
import com.mockuai.tradecenter.core.domain.TradePaymentConfigDO;

public interface TradePaymentConfigDAO {

	public List<TradePaymentConfigDO> queryTradePaymentConfig(TradePaymentConfigQTO record);
}
