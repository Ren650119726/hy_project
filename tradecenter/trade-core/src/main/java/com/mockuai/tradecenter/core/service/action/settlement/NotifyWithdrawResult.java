package com.mockuai.tradecenter.core.service.action.settlement;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.settlement.NotifyWithdrawResultDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.SellerTransLogManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

public class NotifyWithdrawResult implements Action {
	private static final Logger log = LoggerFactory.getLogger(ApplyWithDraw.class);

	
	@Resource
	private SellerTransLogManager sellerTransLogManager;
	
	@Override
	public TradeResponse<?> execute(RequestContext context) throws TradeException {
		TradeResponse<?> response = null;
		Request request = context.getRequest();
		if (request.getParam("notifyWithdrawResultDTO") == null) {
			log.error("processWithdrawDTO is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "notifyWithdrawResultDTO is null");
		}
		NotifyWithdrawResultDTO notifyWhthdrawResultDTO  = (NotifyWithdrawResultDTO) request.getParam("notifyWithdrawResultDTO");
		boolean processNotifyResult = sellerTransLogManager.processWithdrawNotify(notifyWhthdrawResultDTO);
		if(processNotifyResult)
				response =  ResponseUtils.getSuccessResponse(true);
		else
			  response =  ResponseUtils.getSuccessResponse(false);
		
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.NOTIFY_WITHDRAW_RESULT.getActionName();
	}

}
