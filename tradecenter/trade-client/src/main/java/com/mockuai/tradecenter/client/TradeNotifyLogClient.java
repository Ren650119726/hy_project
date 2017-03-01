package com.mockuai.tradecenter.client;

import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.TradeNotifyLogDTO;
import com.mockuai.tradecenter.common.domain.TradeNotifyLogQTO;
import com.mockuai.tradecenter.common.domain.message.WxTemplateDTO;

import java.util.List;

public interface TradeNotifyLogClient {

    Response<List<TradeNotifyLogDTO>>  queryTradeNotifyLog(String appKey, TradeNotifyLogQTO tradeNotifyLogQTO);

}
