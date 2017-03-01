package com.mockuai.tradecenter.core.service.action.settlement;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.base.ClientExecutorFactory;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

public class ProcessWithdraw implements Action {
	private static final Logger log = LoggerFactory.getLogger(ApplyWithDraw.class);
	@Resource
	private ClientExecutorFactory clientExecutorFactory;

	@Autowired
	private AppManager appManager;
	
	@Override
	public TradeResponse<?> execute(RequestContext context) throws TradeException {
		TradeResponse<?> response = null;
		Request request = context.getRequest();
		if (request.getParam("processWithdrawDTO") == null) {
			log.error("processWithdrawDTO is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "processWithdrawDTO is null");
		}
		ClientExecutor clientExecutor = clientExecutorFactory.getExecutor("aliPayBatchTrans");
		if (null == clientExecutor) {
			return new TradeResponse<PaymentUrlDTO>(ResponseCode.BIZ_NOT_EXIST_PAYMENT);
		}
		String bizCode = "mockuai_demo";
		BizInfoDTO bizInfo = appManager.getBizInfo(bizCode);
		if(null!=bizInfo&&null!=bizInfo.getBizPropertyMap()){
			context.put("bizPropertyMap", bizInfo.getBizPropertyMap());
		}
		
		
		PaymentUrlDTO paymentUrlDTO = clientExecutor.getPaymentUrl(context);
		response = new TradeResponse(paymentUrlDTO);
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.PROCESS_WITHDRAW.getActionName();
	}

}
