package com.mockuai.tradecenter.core.service.action.refund;



import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumOrderStatus;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.RefundOrderItemManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

/**
 * 审核用户退货申请  0不同意 1同意
 */
public class AuditRefundApply implements Action {
	private static final Logger log = LoggerFactory.getLogger(ApplyRefund.class);

	@Resource
	private OrderManager orderManager;

	@Resource
	private RefundOrderItemManager refundOrderItemManager;
	
	
	@SuppressWarnings("unchecked")
	public TradeResponse<Boolean> execute(RequestContext context){
		Request request = context.getRequest();
		TradeResponse<Boolean> response = null;

		if (request.getParam("refundOrderItemDTO") == null) {
			log.error("refundOrderItemDTO is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "refundOrderItemDTO is null");
		}
		
		RefundOrderItemDTO refundOrderItemDTO = (RefundOrderItemDTO) request.getParam("refundOrderItemDTO");

		// 必要的字段
		String validatorResult;
		try {
			validatorResult = refundOrderItemManager.validator4RefundAudit(refundOrderItemDTO);
			if(StringUtils.isNotBlank(validatorResult)){
				log.error(validatorResult);
				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, validatorResult);
			}
		} catch (TradeException e1) {
			log.error("AuditRefundApply validator error",e1);
		}
		

		OrderDO order = null;
		try{
			order = this.orderManager.getActiveOrder(refundOrderItemDTO.getOrderId(), refundOrderItemDTO.getUserId());
		}catch(TradeException e){
			return ResponseUtils.getFailResponse(e.getResponseCode());
		}

		if(order ==null){
			log.error("order doesn't exist");
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST,"order not exist");
		}


//		if(order.getOrderStatus()!=Integer.parseInt(EnumOrderStatus.REFUND_APPLY.getCode())&&
//				order.getOrderStatus()!=Integer.parseInt(EnumOrderStatus.PARTIAL_REFUND.getCode())){
//			log.error("order status error");
//			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ORDER_STATUS_ERROR);
//		}

		try{
			Boolean result = refundOrderItemManager.auditOrderItemRefund(refundOrderItemDTO);
			response = ResponseUtils.getSuccessResponse(result);

		}catch(TradeException e){
			log.error("auditRe error : " + e);
			return ResponseUtils.getFailResponse(e.getResponseCode());
		}
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.AUDIT_RETURN_APPLY.getActionName();
	}

}
