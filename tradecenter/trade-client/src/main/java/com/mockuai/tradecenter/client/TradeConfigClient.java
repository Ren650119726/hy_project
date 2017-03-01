package com.mockuai.tradecenter.client;

import java.util.List;

import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.TradeConfigDTO;
import com.mockuai.tradecenter.common.domain.TradeConfigQTO;


public interface TradeConfigClient {
	
	Response<Boolean> updateTradeCofig(List<TradeConfigDTO> configAttrs,String appKey);
	
	Response<List<TradeConfigDTO>> queryTradeConfig(TradeConfigQTO query,String appKey);
	
}
