package com.mockuai.tradecenter.core.dao;

import com.mockuai.tradecenter.common.domain.TradeNotifyLogQTO;
import com.mockuai.tradecenter.core.domain.TradeNotifyLogDO;

import java.util.List;

public interface TradeNotifyLogDAO {

	 Long addTradeNofifyLog(TradeNotifyLogDO record);
	
	 TradeNotifyLogDO getTradeNofityLog(TradeNotifyLogQTO query);

    /**
     * 后台查询
     * @param param
     * @return
     */
     List<TradeNotifyLogDO> queryTradeNotifyLog(TradeNotifyLogQTO param);
}
