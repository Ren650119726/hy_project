package com.mockuai.tradecenter.core.service.action.refund;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.RefundOrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

public class QueryRefundOrder implements Action {
	private static final Logger log = LoggerFactory.getLogger(QueryRefundOrder.class);
	private static final int DEFAULT_START = 0;
	private static final int DEFAULT_PAGE_SIZE = 20;

	@Autowired
	RefundOrderManager refundOrderManager;

	@Override
	public TradeResponse<?> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<List<OrderDTO>> response = null;
		if (request.getParam("orderQTO") == null) {
			log.error("orderQTO is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderQTO is null");
		}

		String appKey = (String) context.get("appKey");
		String bizCode = (String) context.get("bizCode");
		OrderQTO orderQTO = (OrderQTO) request.getParam("orderQTO");

		if (null == orderQTO.getCount()) {
			orderQTO.setCount(DEFAULT_PAGE_SIZE);
		}

		if (null == orderQTO.getOffset()) {
			orderQTO.setOffset(DEFAULT_START);
		}
		orderQTO.setBizCode(bizCode);
		Long totalCount = refundOrderManager.getRefundStatusOrderCount(orderQTO);

		List<OrderDTO> list = refundOrderManager.queryRefundStatusOrderList(orderQTO, appKey);

		response = ResponseUtils.getSuccessResponse(list);
		response.setTotalCount(totalCount); // 总记录数
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_REFUND_ORDER.getActionName();
	}

}
