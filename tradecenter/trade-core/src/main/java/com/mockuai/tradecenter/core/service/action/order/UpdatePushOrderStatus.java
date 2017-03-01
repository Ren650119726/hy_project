package com.mockuai.tradecenter.core.service.action.order;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
 * 批量推送订单
 * @author LY
 */
@Service
public class UpdatePushOrderStatus implements Action{
	private static final Logger log = LoggerFactory.getLogger(UpdatePushOrderStatus.class);
	
	@Resource 
	private OrderManager orderManager;
	
	public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<Boolean> response = null;
		if(request.getParam("orderQTOList") == null){
			log.error("orderQTOList is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"orderQTOList is null");
		}
		List<OrderQTO> orderQTOList= (List<OrderQTO>)request.getParam("orderQTOList") ;
		
		if(CollectionUtils.isEmpty(orderQTOList)){
			log.error("orderQTOList is empty ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"orderQTOList is empty");
		}
		
		try{
			if(this.orderManager.pushOrders(orderQTOList)){
				response = ResponseUtils.getSuccessResponse(true);
			}else{
				response = ResponseUtils.getFailResponse(ResponseCode.SYS_E_DATABASE_ERROR);
			}
			
		}catch(TradeException e){
			log.error("db error : " + e);
			throw new TradeException(e.getResponseCode());
		}
		
		return response;
	}
	
	@Override
	public String getName() {
		return ActionEnum.UPDATE_PUSH_ORDER_STATUS.getActionName();
	}
}
