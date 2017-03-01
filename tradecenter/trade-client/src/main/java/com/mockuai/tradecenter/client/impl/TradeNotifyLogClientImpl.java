package com.mockuai.tradecenter.client.impl;

import com.mockuai.tradecenter.client.TradeNotifyLogClient;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.TradeNotifyLogDTO;
import com.mockuai.tradecenter.common.domain.TradeNotifyLogQTO;

import javax.annotation.Resource;
import java.util.List;

public class TradeNotifyLogClientImpl implements TradeNotifyLogClient{

	@Resource
	private TradeService tradeService;
	

    public Response<List<TradeNotifyLogDTO>> queryTradeNotifyLog(String appKey, TradeNotifyLogQTO tradeNotifyLogQTO) {
        BaseRequest request = new BaseRequest();
        request.setParam("appKey",appKey);
        request.setParam("tradeNotifyQTO",tradeNotifyLogQTO);
        request.setCommand(ActionEnum.QUERY_TRADE_NOTIFY_LOG.getActionName());
        return tradeService.execute(request);
    }


}
