package com.mockuai.tradecenter.core.service.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.TradeConfigDTO;
import com.mockuai.tradecenter.common.domain.TradeConfigQTO;
import com.mockuai.tradecenter.core.dao.TradeConfigDAO;
import com.mockuai.tradecenter.core.domain.TradeConfigDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.util.DozerBeanService;

public class QueryTradeConfig implements Action {
	private static final Logger log = LoggerFactory.getLogger(QueryTradeConfig.class);

	@Resource
	private TradeConfigDAO tradeConfigDAO;
	@Resource
	private DozerBeanService dozerBeanService;

	@Override
	public TradeResponse execute(RequestContext context) throws TradeException {
		@SuppressWarnings("rawtypes")
		TradeResponse<List<TradeConfigDTO>> response = new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
		String bizCode = (String) context.get("bizCode");
		TradeConfigQTO query = new TradeConfigQTO();
		query.setBizCode(bizCode);
		List<TradeConfigDO> tradeConfigDOs = tradeConfigDAO.queryTradeConfig(query);
		List<TradeConfigDTO> tradeConfigDTOs = new ArrayList<TradeConfigDTO>();
		if(tradeConfigDOs.isEmpty()==false){
			tradeConfigDTOs = dozerBeanService.coverList(tradeConfigDOs, TradeConfigDTO.class);
		}
		return ResponseUtils.getSuccessResponse(tradeConfigDTOs);
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_TRADE_CONFIG.getActionName();
	}

}
