package com.mockuai.tradecenter.core.service.action.settlement;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.settlement.ApplyFrozenDTO;
import com.mockuai.tradecenter.common.enums.EnumChannelType;
import com.mockuai.tradecenter.core.dao.SellerMoneyDAO;
import com.mockuai.tradecenter.core.domain.SellerMoneyDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.manager.SellerTransLogManager;
import com.mockuai.tradecenter.core.manager.ShopManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.TradeUtil.ShopType;
import com.mockuai.tradecenter.core.util.TradeUtil.WithdrawType;

public class ApplyFrozen implements Action {

	private static final Logger log = LoggerFactory.getLogger(ApplyFrozen.class);
	
	@Resource
	private SellerTransLogManager sellerTransLogManager;
	@Autowired
	private AppManager appManager;
	@Resource
	private ShopManager shopManager;
	
	@Autowired
	SellerMoneyDAO sellerMoneyDAO;
	
	
	@Override
	public String getName() {
		return ActionEnum.APPLY_FROZEN.getActionName();
	}

	@Override
	public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
		 Request request = context.getRequest();
	        if (request.getParam("applyFrozenDTO") == null) {
	            log.error("applyFrozenDTO is null");
	            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "applyFrozenDTO is null");
	        }
	        String appKey = (String) context.get("appKey");
	        ApplyFrozenDTO dto = (ApplyFrozenDTO) request.getParam("applyFrozenDTO");
	        if(dto.getAmount()==null){
	        	 log.error("frozen amount is null");
	        	 return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "frozen amount is null");
	        }
	        if(dto.getReason()==null){
	        	log.error("frozen reason is null");
	        	return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "frozen reason is null");
	        }
	        if(dto.getType()==null){
	        	log.error("frozen type is null");
	        	return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "frozen type is null");
	        }
	        String bizCode = (String) context.get("bizCode");
	        dto.setBizCode(bizCode);
	        BizInfoDTO bizInfo = appManager.getBizInfo(bizCode);
	      
	        boolean processResult = sellerTransLogManager.recordTransLogForForzen(dto);
	        
	        if(processResult){
	        	return new TradeResponse<Boolean>(ResponseCode.RESPONSE_SUCCESS);
	        }else{
	        	return new TradeResponse(ResponseCode.SYS_E_REMOTE_CALL_ERROR,"申请冻结出错");
	        }
	}
}
