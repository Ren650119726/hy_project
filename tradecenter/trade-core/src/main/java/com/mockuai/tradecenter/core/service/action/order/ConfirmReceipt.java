package com.mockuai.tradecenter.core.service.action.order;

import java.util.Date;

import javax.annotation.Resource;

import com.mockuai.tradecenter.core.domain.OrderDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.DataManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.TradeUtil.RefundMark;
import com.mockuai.tradecenter.common.constant.ActionEnum;


/**
 * 整单签收处理类
 * @author cwr
 */
public class ConfirmReceipt implements Action{

	private static final Logger log = LoggerFactory.getLogger(ConfirmReceipt.class);
	
	@Resource
	private OrderManager orderManager;
	
	@Resource
	private DataManager dataManager;

	public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<Boolean> response = null;
		if(request.getParam("userId") == null){
			log.error("userId is missing ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"userId is missing");
		}else if(request.getParam("orderId") == null){
			log.error("orderId is missing");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"orderId is missing");
		}
			
		long userId =(Long)request.getParam("userId");
		long orderId = (Long)request.getParam("orderId");

		OrderDO order = this.orderManager.getActiveOrder(orderId, userId);
		if(order == null){
			log.error("order doesn't exist");
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "order doesn't exist");
		}

		//订单状态检查
		if(order.getOrderStatus() != TradeConstants.Order_Status.DELIVERIED){
			return ResponseUtils.getFailResponse(ResponseCode.BZI_E_ORDER_UNDELIVERED_CANNOT_RECEIPT);
		}
		
		if( order.getRefundMark()==RefundMark.REFUNDING_MARK ){
			return ResponseUtils.getFailResponse(ResponseCode.HAS_REFUND_ITEM_CAN_NOT_OPERATE);
		}
		
		 String appKey = (String) context.get("appKey");
		// 按照整单发货 整单签收处理
		int result = 0;
		try {
			
			// 更新订单主表的配送状态
			int deliveryStatus = TradeConstants.DeliveryStatus.ALL_RECEIVAL;  // 全部签收
			result = this.orderManager.confirmReceival(orderId, userId, deliveryStatus);
			
//			dataManager.addWaitCommentStatusBuriedPoint(order, appKey);
			
			context.put("order", order);
			
		} catch (TradeException e) {
			log.error("db error： " + e);
			response = ResponseUtils.getFailResponse(e.getResponseCode());
		}
		if(result > 0){
			response = ResponseUtils.getSuccessResponse(true);
		}else {
			log.error("updated failed");
			response = ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST);
		}
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.CONFIRM_RECEIVAL.getActionName();
	}
	
}
