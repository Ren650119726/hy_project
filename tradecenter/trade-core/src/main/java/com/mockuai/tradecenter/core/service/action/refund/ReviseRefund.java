package com.mockuai.tradecenter.core.service.action.refund;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.RefundOrderItemManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

public class ReviseRefund implements Action{
	private static final Logger log = LoggerFactory.getLogger(ReviseRefund.class);

	@Autowired
	RefundOrderItemManager refundOrderItemMng;
	
	@Override
	public TradeResponse execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<Boolean> response = null;
		
		if (request.getParam("refundOrderItemDTOs") == null) {
			log.error("refundOrderItemDTOs is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "refundOrderItemDTOs is null");
		}
		
		List<RefundOrderItemDTO> refundOrderItemDTOs = (List<RefundOrderItemDTO>) request.getParam("refundOrderItemDTOs");
		if(null==refundOrderItemDTOs||refundOrderItemDTOs.isEmpty()){
			log.error("refundOrderItemDTOs is empty");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "refundOrderItemDTOs is empty");
		}
		
		//for validator
		for(RefundOrderItemDTO refundOrderItemDTO:refundOrderItemDTOs){
			if(null==refundOrderItemDTO.getOrderId()){
				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderId is null");
			}
			if(null==refundOrderItemDTO.getOrderItemId()){
				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderItemId is null");
			}
			if(null==refundOrderItemDTO.getRefundStatus()){
				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "refundStatus is null");
			}
		}
		
		//逻辑处理
		Boolean result = refundOrderItemMng.batchReviseRefund(refundOrderItemDTOs);
		if(result){
			response = ResponseUtils.getSuccessResponse(result);
		}else{
			response = ResponseUtils.getFailResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION,"批量处理失败");
		}
		
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.REVISE_REFUND.getActionName();
	}
}
