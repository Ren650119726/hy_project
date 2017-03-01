package com.mockuai.tradecenter.core.service.action.settlement;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.settlement.WithdrawDTO;
import com.mockuai.tradecenter.common.enums.EnumWithdrawStatus;
import com.mockuai.tradecenter.common.util.StringUtil;
import com.mockuai.tradecenter.core.domain.WithdrawInfoDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.SellerTransLogManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.TradeUtil.WithdrawAuditStatus;


public class RefuseWithdrawApply implements Action {

	private static final Logger log = LoggerFactory.getLogger(RefuseWithdrawApply.class);
	
	@Autowired
	SellerTransLogManager sellerTransLogManager;
	
	@Override
	public String getName() {
		return ActionEnum.REFUSE_WITHDRAW_APPLY.getActionName();
	}

	@Override
	public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
		 Request request = context.getRequest();
	        if (request.getParam("withdrawDTO") == null) {
	            log.error("withdrawDTO is null");
	            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "withdrawDTO is null");
	        }
	        WithdrawDTO dto = (WithdrawDTO) request.getParam("withdrawDTO");
	        if(dto.getId()==null){
	        	 return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "apply id is null");
	        }
	        if(StringUtil.isBlank(dto.getRefuseReason())){
	        	return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "refuseReason is null");
	        }
	        String bizCode = (String) context.get("bizCode");
	        dto.setBizCode(bizCode);
	        
	        boolean processRefuseWithdraw = sellerTransLogManager.processRefuseWithdraw(dto);
	        
	        if(processRefuseWithdraw){
	        	return new TradeResponse<Boolean>(ResponseCode.RESPONSE_SUCCESS);
	        }else{
	        	return new TradeResponse(ResponseCode.SYS_E_REMOTE_CALL_ERROR,"拒绝出错");
	        }
	}
}
