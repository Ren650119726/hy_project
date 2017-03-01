package com.mockuai.tradecenter.core.service.action.settlement;

import java.util.Date;
import java.util.List;

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


public class BatchAduitWithdraw implements Action {

	private static final Logger log = LoggerFactory.getLogger(BatchAduitWithdraw.class);
	
	@Autowired
	SellerTransLogManager sellerTransLogManager;
	
	@Override
	public String getName() {
		return ActionEnum.BATCH_AUDIT_WITHDRAW.getActionName();
	}

	@Override
	public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
		 Request request = context.getRequest();
	        if (request.getParam("withdrawDTOList") == null) {
	            log.error("withdrawDTOList is null");
	            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "withdrawDTOList is null");
	        }
	        List<WithdrawDTO> withdrawDTOs = (List<WithdrawDTO>) request.getParam("withdrawDTOList");
	        
	        if(withdrawDTOs.isEmpty()){
	        	return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "withdrawDTOList is empty");
	        }
	        
	        for(WithdrawDTO withdrawDTO:withdrawDTOs){
	        	if(null==withdrawDTO){
	        		return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "id not null");
	        	}
	        }
	        String bizCode = (String) context.get("bizCode");
	        
	        
	      
	        
	        boolean processRefuseWithdraw = sellerTransLogManager.batchProcessAuditWithdraw(bizCode, withdrawDTOs);
	        
	        if(processRefuseWithdraw){
	        	return new TradeResponse<Boolean>(ResponseCode.RESPONSE_SUCCESS);
	        }else{
	        	return new TradeResponse(ResponseCode.SYS_E_REMOTE_CALL_ERROR,"拒绝出错");
	        }
	}
}
