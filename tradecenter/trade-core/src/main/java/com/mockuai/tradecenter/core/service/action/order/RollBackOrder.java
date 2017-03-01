package com.mockuai.tradecenter.core.service.action.order;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

/**
 * 回滚订单
 * @author LY
 */
public class RollBackOrder implements Action{
	private static final Logger log = LoggerFactory.getLogger(RollBackOrder.class);
	
	@Resource 
	private OrderManager orderManager;
	
	public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<Boolean> response = null;
		if(request.getParam("orderQTO") == null){
			log.error("orderQTO is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"orderQTO is null");
		}
		OrderQTO orderQTO= (OrderQTO)request.getParam("orderQTO") ;
		
		
		try{
			
			if(this.orderManager.rollBackOrder(orderQTO)){
				response = ResponseUtils.getSuccessResponse(true);
			}else{
				response = ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST);
			}
			
		}catch(TradeException e){
			log.error("db error : " + e);
			throw new TradeException(e.getResponseCode());
		}
		
		return response;
	}
	
	@Override
	public String getName() {
		return ActionEnum.ROLLBACK_ORDER.getActionName();
	}
}
