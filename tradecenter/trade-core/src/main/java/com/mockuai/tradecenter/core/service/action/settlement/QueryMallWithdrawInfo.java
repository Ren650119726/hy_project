package com.mockuai.tradecenter.core.service.action.settlement;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.qto.ShopQTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.settlement.WithdrawDTO;
import com.mockuai.tradecenter.common.domain.settlement.WithdrawQTO;
import com.mockuai.tradecenter.common.enums.EnumWithdrawStatus;
import com.mockuai.tradecenter.common.util.StringUtil;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.ShopManager;
import com.mockuai.tradecenter.core.manager.WithdrawManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

public class QueryMallWithdrawInfo implements Action {

	private static final Logger log = LoggerFactory.getLogger(QuerySellerTransLog.class);
	
	@Resource
	private WithdrawManager withdrawManager;
	
	@Autowired
	private ShopManager shopManager;
	
	@Override
	public TradeResponse<?> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<List<WithdrawDTO>> response = null;
		if(request.getParam("withdrawQTO") == null){
			log.error("withdrawQTO is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"withdrawQTO is null");
		}
		String appKey = (String) context.get("appKey");
		WithdrawQTO query = (WithdrawQTO) request.getParam("withdrawQTO");
		if(StringUtil.isNotBlank(query.getShopName())){
			ShopQTO shopQuery = new ShopQTO();
			shopQuery.setShopName(query.getShopName());
			List<ShopDTO> shopList = shopManager.queryShop(shopQuery, appKey);
			if(shopList.isEmpty()==false){
				List<Long> shopIds = new ArrayList<Long>();
				for(ShopDTO shopDTO:shopList){
					shopIds.add(shopDTO.getId());
				}
				query.setShopIds(shopIds);
			}
		}
		String bizCode = (String) context.get("bizCode");
		query.setBizCode(bizCode);
		if(null!=query.getStatus()){
			query.setStatus(EnumWithdrawStatus.getByOldCode(query.getStatus()).getCode());
		}
		List<WithdrawDTO> dataList = (List<WithdrawDTO>) withdrawManager.queryWithdraw(query);
		
		if(dataList.isEmpty()==false){
			for(WithdrawDTO withdrawDTO:dataList){
				if(null!=withdrawDTO.getShopId()){
					ShopDTO shopDTO = shopManager.getShopDTO(withdrawDTO.getSellerId(),appKey);
					if(null!=shopDTO)
						withdrawDTO.setShopName(shopDTO.getShopName());
				}
			}
		}
		
		response = ResponseUtils.getSuccessResponse(dataList);
		Long totalCount = withdrawManager.getQueryCount(query);
		response.setTotalCount(totalCount); // 总记录数 
		return response;
		
		
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_MALL_WITHDRAW_INFO.getActionName();
	}

}
