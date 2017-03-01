package com.mockuai.tradecenter.core.service.action.order;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.MarketingManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.Action;

/**
* 订单结算接口
*/
public class OrderSettlementGet implements Action {
	private static final Logger log = LoggerFactory.getLogger(OrderSettlementGet.class);

	@Resource
	private MarketingManager marketingManager;


	public TradeResponse<SettlementInfo> execute(RequestContext context)
			throws TradeException {
		Request request = context.getRequest();
		List<MarketItemDTO> itemList = (List<MarketItemDTO>) request.getParam("itemList");
		Long userId = (Long) request.getParam("userId");
		Long consigneeId = (Long) request.getParam("consigneeId");
		String appKey = (String) context.get("appKey");
		
		try {
			SettlementInfo settlementInfo = marketingManager.getSettlementInfoMar(userId, itemList, consigneeId, appKey);
			return new TradeResponse<SettlementInfo>(settlementInfo);
		} catch (TradeException e) {
			log.error(" errorCode : "+e.getResponseCode().getCode());
			return new TradeResponse<SettlementInfo>(e.getResponseCode().getCode(), e.getMessage());
		}
		
	}
	
	@Override
	public String getName() {
		return ActionEnum.ORDER_SETTLEMENT_GET.getActionName();
	}

}
