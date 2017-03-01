package com.mockuai.tradecenter.core.service.action.order;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

/**
 * 修改订单标注 （加星）
 * @author liuchao
 */
public class UpdateOrderAsterisksMark implements Action{
	private static final Logger log = LoggerFactory.getLogger(UpdateOrderAsterisksMark.class);
	
	@Resource 
	private OrderManager orderManager;
	
	public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<Boolean> response = null;
		if(request.getParam("orderId") == null){
			log.error("orderId is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"orderId is null");
			
		}else if(request.getParam("userId") == null){
			log.error("userId is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"userId is null");
		}else if(request.getParam("isAsteriskMark")==null){ 
			log.error("isAsteriskMark is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"isAsteriskMark is null");
		}
		
		long orderId = (Long) request.getParam("orderId");
		long userId = (Long) request.getParam("userId");
		String isMark = (String) request.getParam("isAsteriskMark");
		
		
		boolean isAsteriskMark = false;
		if(isMark.equalsIgnoreCase("y")){
			isAsteriskMark = true;
		}
		
		OrderDO order = this.orderManager.getActiveOrder(orderId, userId);
		if(order == null){
			log.error("order doesn't exist orderId:" +orderId + " userId:" + userId );
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST,"order doesn't exist");
		}
		
		try{
			
			if(this.orderManager.updateOrderAsteriskMark(orderId, userId, isAsteriskMark)){
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
		return ActionEnum.UPDATE_ORDER_ASTERISKS_MARK.getActionName();
	}
}
