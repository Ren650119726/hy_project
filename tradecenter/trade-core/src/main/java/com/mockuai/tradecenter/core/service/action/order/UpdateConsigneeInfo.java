//package com.mockuai.tradecenter.core.service.action.order;
//
//import javax.annotation.Resource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.mockuai.tradecenter.common.api.Request;
//import com.mockuai.tradecenter.common.api.TradeResponse;
//import com.mockuai.tradecenter.common.constant.ResponseCode;
//import com.mockuai.tradecenter.core.domain.OrderDO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//import com.mockuai.tradecenter.core.manager.OrderManager;
//import com.mockuai.tradecenter.core.service.RequestContext;
//import com.mockuai.tradecenter.core.service.ResponseUtils;
//import com.mockuai.tradecenter.core.service.action.Action;
//import com.mockuai.tradecenter.common.constant.ActionEnum;
//
///**
// * 修改收货地址
// *
// */
//public class UpdateConsigneeInfo implements Action{
//	private static final Logger log = LoggerFactory.getLogger(UpdateConsigneeInfo.class);
//
//	@Resource
//	private OrderManager orderManager;
//
//
//	public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
//		Request request = context.getRequest();
//		TradeResponse<Boolean> response = null;
//		if(request.getParam("orderId") == null){
//			log.error("orderId is missing");
//			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"orderId is missing");
//		}else if( request.getParam("userId") == null){
//			log.error("userId is missing");
//			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"userId is missing");
//		}
//		// 不能都为空
//		if(request.getParam("consignee") == null && request.getParam("phone") ==null && request.getParam("address") == null){
//			log.error("consignee info is missing");
//			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"consignee info is missing");
//		}
//
//		long userId =(Long)request.getParam("userId");
//		long orderId = (Long)request.getParam("orderId");
//		String consignee = (String)request.getParam("consignee");
//		String phone = (String)request.getParam("phone");
//		String address = (String)request.getParam("address");
//
//		// 查看订单是否存在
//		OrderDO order = this.orderManager.getActiveOrder(orderId, userId);
//		if(order == null){
//			log.error("order doesn't exist orderId:" +orderId + " userId:" + userId );
//			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST,"order doesn't exist");
//		}
//
//		long sellerId = order.getSellerId();
//		int result = 0,result2=0;
//		try {
//			//同步更新买家和卖家订单表
//			result = this.orderManager.updateConsigneeInfo(orderId, userId, consignee, phone, address);
//			result2 = this.sellerOrderManager.updateConsigneeInfo(orderId, supplierId, consignee, phone, address);
//
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			response = ResponseUtils.getFailResponse(ResponseCode.SYS_E_DATABASE_ERROR);
//		}
//
//		if(result > 0 && result2 > 0){
//			response = ResponseUtils.getSuccessResponse(true);
//		}else{
//			log.error("updated failed");
//			response = ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST,"updated failed");
//		}
//		return response;
//	}
//
//	@Override
//	public String getName() {
//		return ActionEnum.UPDATE_CONSINEE_INFO.getActionName();
//
//	}
//
//}
