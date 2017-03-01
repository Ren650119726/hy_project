package com.mockuai.tradecenter.core.service.action.order;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.TradePaymentConfigDTO;
import com.mockuai.tradecenter.common.domain.TradePaymentConfigQTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.TradePaymentConfigManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

@Service
public class GetPaymentConfig implements Action{
	private static final Logger log = LoggerFactory.getLogger(GetPaymentConfig.class);
	
	@Resource 
	private TradePaymentConfigManager tradePaymentConfigManager;
	

	public TradeResponse<List<TradePaymentConfigDTO>> execute(RequestContext context) throws TradeException {
		
		log.info(" GetPaymentConfig start ");
		
		Request request = context.getRequest();
		TradeResponse<List<TradePaymentConfigDTO>> response = null;
		
		if(request.getParam("tradePaymentConfigQTO") == null){
			log.error("tradePaymentConfigQTO is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"tradePaymentConfigQTO is null");
		}

		TradePaymentConfigQTO tradePaymentConfigQTO = (TradePaymentConfigQTO)request.getParam("tradePaymentConfigQTO");
		
		String appKey = (String) context.get("appKey");
		
		String bizCode = (String) context.get("bizCode");
		
		tradePaymentConfigQTO.setBizCode(bizCode);
		
		List<TradePaymentConfigDTO> tradePaymentConfigDTOList = new ArrayList<TradePaymentConfigDTO>();

		try{
			
			tradePaymentConfigDTOList  = tradePaymentConfigManager.queryTradePaymentConfig(tradePaymentConfigQTO);			

			response = ResponseUtils.getSuccessResponse(tradePaymentConfigDTOList);
			
			log.info(" GetPaymentConfig end ");
			
			return response;
		}catch(TradeException e){
			log.error("GetPaymentConfig error: ",e);
			return ResponseUtils.getFailResponse(e.getResponseCode());
		}
	}
	

	
	@Override
	public String getName() {
		return ActionEnum.GET_PAYMENT_CONFIG.getActionName();
	}
}
