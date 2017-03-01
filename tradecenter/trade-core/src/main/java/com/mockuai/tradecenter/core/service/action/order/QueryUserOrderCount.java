package com.mockuai.tradecenter.core.service.action.order;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;


public class QueryUserOrderCount implements Action{
	private static final Logger log = LoggerFactory.getLogger(QueryUserOrderCount.class);
	
	@Resource 
	private OrderManager orderManager;
	
	public TradeResponse<Integer> execute(RequestContext context) throws TradeException {
		
		log.info(" get QueryUserOrderCount start ");
		
		Request request = context.getRequest();
		TradeResponse<Integer> response = null;
		
		if(request.getParam("orderQTO") == null){
			log.error("orderQTO is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"orderQTO is null");
		}
		
		OrderQTO orderQTO = (OrderQTO) request.getParam("orderQTO");
		String appKey = (String) context.get("appKey");
				
		OrderDO orderDO = null;
		try{
			int result = orderManager.queryUserOrderCount(orderQTO);

			response = ResponseUtils.getSuccessResponse(result);
			
			log.info(" get QueryUserOrderCount end ");
			
			return response;
		}catch(TradeException e){
			log.error("QueryUserOrderCount error: ",e);
			return ResponseUtils.getFailResponse(e.getResponseCode());
		}
	}
	
	@Override
	public String getName() {
		return ActionEnum.QUERY_USER_ORDER_COUNT.getActionName();
	}
}
