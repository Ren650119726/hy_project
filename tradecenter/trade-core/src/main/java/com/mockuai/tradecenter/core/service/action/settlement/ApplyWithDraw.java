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
import com.mockuai.tradecenter.common.domain.settlement.ApplyWithDrawDTO;
import com.mockuai.tradecenter.common.domain.settlement.SellerMoneyDTO;
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

/**
 * 申请提现
 *
 */
public class ApplyWithDraw implements Action {

	private static final Logger log = LoggerFactory.getLogger(ApplyWithDraw.class);
	
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
		return ActionEnum.APPLY_WITHDRAW.getActionName();
	}

	@Override
	public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
		 Request request = context.getRequest();
	        if (request.getParam("withdrawDTO") == null) {
	            log.error("withdrawDTO is null");
	            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "withdrawDTO is null");
	        }
	        String appKey = (String) context.get("appKey");
	        ApplyWithDrawDTO dto = (ApplyWithDrawDTO) request.getParam("withdrawDTO");
	        if(StringUtils.isBlank(dto.getName())||StringUtils.isBlank(dto.getAccount())||null==dto.getAmount()){
	        	 log.error("withdrawDTO is null");
	        	 return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "name or account or amout is null");
	        }
	        String bizCode = (String) context.get("bizCode");
	        dto.setBizCode(bizCode);
	        BizInfoDTO bizInfo = appManager.getBizInfo(bizCode);
	        BizPropertyDTO isPayByMockuai = bizInfo.getBizPropertyMap()
					.get(BizPropertyKey.IS_PAY_BY_MOCKUAI);
	    	if(null!=isPayByMockuai&&isPayByMockuai.getValue().equals("1")){ // 代表走魔筷的帐
	    		dto.setOperByMockuai(1);
	    	}else{
	    		dto.setOperByMockuai(0);
	    	}
	        
	        String paymentId = EnumChannelType.toMap().get(dto.getChannel());
	        if(null==paymentId)
	        	 return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "paymentId is null");
	        
	        if(null!=dto.getSellerId()){
	        	//TODO 增加shop信息
		        ShopDTO shop = null;
				try{
					shop = shopManager.getShopDTO(dto.getSellerId(), appKey);
				}catch(Exception e){
					log.error("get shop error",e);
				}
				if(shop!=null){
					dto.setShopId(shop.getId());
				}
				dto.setType(WithdrawType.SHOP);
	        }else{
	        	dto.setType(WithdrawType.MALL);
	        }
	        
	        
	        SellerMoneyDO sellerMoneyDO = null;
	        if(null!=dto.getSellerId()){
	        	sellerMoneyDO =  sellerMoneyDAO.getSellerMoneyBySellerId(dto.getSellerId());
	        }else{
	        	sellerMoneyDO = sellerMoneyDAO.getSellerMoney(dto.getBizCode(), ShopType.MALL);
	        }
	       
	        if(null==sellerMoneyDO)
	        	return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "sellerMoney is null");
	        if(sellerMoneyDO.getAvailableBalance()<dto.getAmount())
	        	return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "canUserBalance less than applyWithdraw amount");
	        
	        sellerTransLogManager.recordTransLogForWithDraw(dto);
	        TradeResponse<Boolean> response = new TradeResponse<Boolean>(ResponseCode.RESPONSE_SUCCESS);
			return response;
	}
}
