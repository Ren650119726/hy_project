package com.mockuai.tradecenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.tradecenter.client.TradeConfigClient;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.TradeConfigDTO;
import com.mockuai.tradecenter.common.domain.TradeConfigQTO;

public class TradeConfigClientImpl implements TradeConfigClient {

	@Resource
	private TradeService tradeService;

	@Override
	public Response<Boolean> updateTradeCofig(List<TradeConfigDTO> configAttrs, String appKey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_TRADE_CONFIG.getActionName());
		request.setParam("tradeConfigAttrs", configAttrs);

		request.setParam("appKey", appKey);

		Response response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<List<TradeConfigDTO>> queryTradeConfig(TradeConfigQTO query, String appKey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_TRADE_CONFIG.getActionName());
		request.setParam("tradeConfigQTO", query);

		request.setParam("appKey", appKey);

		Response response = tradeService.execute(request);
		return response;
	}

}
