package com.mockuai.tradecenter.core.service.action;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.common.constant.ActionEnum;

/**
 * 更新备注信息
 * @author cwr
 */
public class UpdateOrderMemo implements Action{
	private static final Logger log = LoggerFactory.getLogger(UpdateOrderMemo.class);
	
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
		}else if(request.getParam("newMemo")==null){
			log.error("newMemo is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"newMemo is null");
		}else if(request.getParam("memoType") == null){
			log.error("memoType is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"memoType is null");
		}
		
		long orderId = (Long) request.getParam("orderId");
		long userId = (Long) request.getParam("userId");
		String newMemo = (String) request.getParam("newMemo");
		int memoType = (Integer)request.getParam("memoType");
		
		OrderDO order = this.orderManager.getActiveOrder(orderId, userId);
		if(order == null){
			log.error("order doesn't exist orderId:" +orderId + " userId:" + userId );
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST,"order doesn't exist");
		}
		
//		long supplierId = order.getSupplierId();
		int result =0;
		Date now = new Date();
		try{
			//同步更新卖家和买家订单表
			// TODO 后续可能在支付成功后才拆单所以在未支付前不需要同步
			result = this.orderManager.updateOrderMemo(orderId, userId, newMemo, memoType);
//			result2 = this.sellerOrderManager.updateOrderMemo(orderId, supplierId, newMemo, memoType,now);
			
		}catch(TradeException e){
			log.error("db error : " + e);
			throw new TradeException(e.getResponseCode());
		}
		
		if(result > 0 ){
			response = ResponseUtils.getSuccessResponse(true);
		}else{
			log.error("updated failed ");
			response = ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST);
		}
		return response;
	}
	
	@Override
	public String getName() {
		return ActionEnum.UPDATE_ORDER_MEMO.getActionName();
	}
}
