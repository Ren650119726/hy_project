package com.mockuai.tradecenter.core.service.action.settlement;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.settlement.ShopDepositDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.SellerTransLogManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

public class AddShopDeposit implements Action {

	private static final Logger log = LoggerFactory.getLogger(AddShopDeposit.class);
	@Resource
	private SellerTransLogManager sellerTransLogManager;
	
	@Override
	public String getName() {
		return ActionEnum.ADD_SHOP_DEPOSIT.getActionName();
	}
	
	@Override
	public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		if (request.getParam("shopDepositDTO") == null) {
			log.error("shopDepositDTO is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "shopDepositDTO is null");
		}
		
		ShopDepositDTO shopDepositDTO = (ShopDepositDTO) request.getParam("shopDepositDTO");
		
		if(shopDepositDTO.getSellerId()==null){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "sellerId is null");
		}
		if(shopDepositDTO.getAmount()==null){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "amount is null");
		}
		if(shopDepositDTO.getAmount()<=0){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "amount 小于等于0");
		}
		String bizCode = (String) context.get("bizCode");
		shopDepositDTO.setBizCode(bizCode);
		
		sellerTransLogManager.recordAddShopDepositTransLog(shopDepositDTO);
		 TradeResponse<Boolean> response = new TradeResponse<Boolean>(ResponseCode.RESPONSE_SUCCESS);
		 return response;
	}
}
