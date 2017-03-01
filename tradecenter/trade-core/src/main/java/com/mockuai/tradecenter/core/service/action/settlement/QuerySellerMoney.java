package com.mockuai.tradecenter.core.service.action.settlement;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.settlement.SellerMoneyDTO;
import com.mockuai.tradecenter.common.domain.settlement.SellerMoneyQTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.SellerTransLogManager;
import com.mockuai.tradecenter.core.manager.ShopManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

/**
 * 查询
 * 
 *
 */
public class QuerySellerMoney implements Action {

	private static final Logger log = LoggerFactory.getLogger(QuerySellerMoney.class);

	@Resource
	private SellerTransLogManager sellerTransLogManager;
	
	@Autowired
	private ShopManager shopManager;

	@Override
	public TradeResponse<?> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<List<SellerMoneyDTO>> response = null;
		if (request.getParam("sellerMoneyQTO") == null) {
			log.error("sellerMoneyQTO is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "sellerMoneyQTO is null");
		}
		String appKey = (String) context.get("appKey");
		SellerMoneyQTO query = (SellerMoneyQTO) request.getParam("sellerMoneyQTO");
		String bizCode = (String) context.get("bizCode");
		query.setBizCode(bizCode);
		
		List<SellerMoneyDTO> list = (List<SellerMoneyDTO>) sellerTransLogManager.querySellerMoney(query);
	
		for(SellerMoneyDTO sellerMoneyDTO:list){
			if(sellerMoneyDTO.getSellerId()!=null){
				try{
					ShopDTO shopDTO = shopManager.getShopDTO(sellerMoneyDTO.getSellerId(), appKey);
					if(null!=shopDTO){
						sellerMoneyDTO.setShopId(shopDTO.getId());
						sellerMoneyDTO.setShopName(shopDTO.getShopName());
					}
				}catch(Exception e){
					
				}catch(Throwable e){
					log.error("doOrderCommentBuriedPoint error",e);
				}
				
			}
		}
		
		response = ResponseUtils.getSuccessResponse(list);
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_SELLER_MONEY.getActionName();
	}

}
