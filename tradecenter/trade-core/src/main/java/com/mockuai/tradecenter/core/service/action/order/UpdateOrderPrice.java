package com.mockuai.tradecenter.core.service.action.order;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.base.ClientExecutorFactory;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

/**
 * 修改价格
 * @author liuchao
 */
public class UpdateOrderPrice implements Action{
	private static final Logger log = LoggerFactory.getLogger(UpdateOrderPrice.class);
	
	@Resource 
	private OrderManager orderManager;
	
	@Resource
    private ClientExecutorFactory clientExecutorFactory;
	
	public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<Boolean> response = null;
		if(request.getParam("orderId") == null){
			log.error("orderId is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"orderId is null");
			
		}else if(request.getParam("userId") == null){
			log.error("userId is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"userId is null");
//		}
//		else if(request.getParam("freight")==null){ //运费
//			log.error("freight is null");
//			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"freight is null");
		}else if(request.getParam("floatingPrice") == null){//浮动价格
			log.error("floatingPrice is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"floatingPrice is null");
		}
		
		long orderId = (Long) request.getParam("orderId");
		long userId = (Long) request.getParam("userId");
		long floatingPrice = (Long) request.getParam("floatingPrice");
		long freight = (Long)request.getParam("freight");
		
		OrderDO order = this.orderManager.getActiveOrder(orderId, userId);
		if(order == null){
			log.error("order doesn't exist orderId:" +orderId + " userId:" + userId );
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST,"order doesn't exist");
		}
		
		if (order.getOrderStatus() != TradeConstants.Order_Status.UNPAID) {
			log.error("only unpaid order can be update price");
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ORDER_STATUS_ERROR,
					"只有未支付订单能修改价格");
		}
		
		String bizCode = (String)context.get("bizCode");
		int result =0;
		try{
			if(order.getPaymentId()==2||order.getPaymentId()==5){
				ClientExecutor wxPayCloseOrder = clientExecutorFactory.getExecutor("wxpayClientForCloseOrder");
				if(null!=wxPayCloseOrder){
						
					
					 	Map<String,BizPropertyDTO> bizPropertyMap =   (Map<String, BizPropertyDTO>) context.get("bizPropertyMap");
				        if(null==bizPropertyMap){
				        	throw new TradeException(bizCode+" bizPropertyMap is null");
				        }
				        
			            context.put("orderDO", order);
			            context.put("bizPropertyMap", bizPropertyMap);
			            wxPayCloseOrder.getPaymentUrl(context);
				}
			}
			
			result = this.orderManager.updateOrderTotalAmountAndDeliveryFee(orderId, userId,floatingPrice, freight);
			
		}catch(TradeException e){
			log.error("db error : " + e);
			throw new TradeException(e.getResponseCode());
		}
		
		if(result > 0){
			response = ResponseUtils.getSuccessResponse(true);
		}else{
			log.error("updated failed ");
			response = ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST);
		}
		return response;
	}
	
	@Override
	public String getName() {
		return ActionEnum.UPDATE_ORDER_PRICE.getActionName();
	}
}
