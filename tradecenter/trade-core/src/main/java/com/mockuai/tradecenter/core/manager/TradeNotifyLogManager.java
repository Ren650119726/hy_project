package com.mockuai.tradecenter.core.manager;

import com.mockuai.tradecenter.common.domain.TradeNotifyLogDTO;
import com.mockuai.tradecenter.common.domain.TradeNotifyLogQTO;
import com.mockuai.tradecenter.core.domain.TradeNotifyLogDO;
import com.mockuai.tradecenter.core.exception.TradeException;

import java.util.List;

public interface TradeNotifyLogManager {
	
	 Long addTradeNotifyLog(TradeNotifyLogDO record)throws TradeException;

	 TradeNotifyLogDO getTradeNotifyLogByOutBillNo(String outBillNo,Integer type)throws TradeException;

	 TradeNotifyLogDO getTradeNotifyLogByOrderId(Long orderId,Integer type)throws TradeException;

    List<TradeNotifyLogDTO> queryTradeNotifyLog( TradeNotifyLogQTO param)throws TradeException;


	
}
