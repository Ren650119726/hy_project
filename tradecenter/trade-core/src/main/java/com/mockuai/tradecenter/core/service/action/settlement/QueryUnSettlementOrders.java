package com.mockuai.tradecenter.core.service.action.settlement;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.SellerTransLogManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

public class QueryUnSettlementOrders implements Action {
	private static final Logger log = LoggerFactory.getLogger(QueryUnSettlementOrders.class);
	@Resource
	private SellerTransLogManager sellerTransLogManager;

	@SuppressWarnings("unchecked")
	@Override
	public TradeResponse<?> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<List<OrderDTO>> response = null;
		if (request.getParam("dataQuery") == null) {
			log.error("dataQuery is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "dataQuery is null");
		}
		OrderQTO query = (OrderQTO) request.getParam("dataQuery");
		if (null == query.getSellerId()) {
			log.error("sellerTransLogQTO.sellerId is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "sellerId is null");
		}
		String bizCode = (String) context.get("bizCode");
		query.setBizCode(bizCode);
		List<OrderDTO> list = (List<OrderDTO>) sellerTransLogManager.queryUnsettlementOrders(query);
		
		response = ResponseUtils.getSuccessResponse(list);
		
		Long count  = sellerTransLogManager.getUnsettlementOrderCount(query);
		
		response.setTotalCount(count);
		return response;
		
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_UNSETTLEMENT_ORDERS.getActionName();
	}

}
