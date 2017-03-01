package com.mockuai.tradecenter.core.dao;

import java.util.List;

import com.mockuai.tradecenter.common.domain.TradeConfigQTO;
import com.mockuai.tradecenter.core.domain.TradeConfigDO;

public interface TradeConfigDAO {

	public Long addTradeConfig(TradeConfigDO record);

	public int deleteTradeConfig(TradeConfigQTO record);

	public List<TradeConfigDO> queryTradeConfig(TradeConfigQTO record);
	
	public int updateTradeConfig(TradeConfigDO record);
	
	public TradeConfigDO getTradeConfig(String bizCode,String attrKey);
}
