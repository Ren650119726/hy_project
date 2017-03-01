package com.mockuai.tradecenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.tradecenter.client.RefundClient;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;

public class RefundClientImpl implements RefundClient {
	@Resource
	private TradeService tradeService;

	@Override
	public Response<Boolean> auditRefund(RefundOrderItemDTO refundOrderItemDTO, String appkey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.AUDIT_RETURN_APPLY.getActionName());
		request.setParam("refundOrderItemDTO", refundOrderItemDTO);
		request.setParam("appKey", appkey);
		Response response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<PaymentUrlDTO> confirmRefund(RefundOrderItemDTO refundOrderItemDTO, String appkey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.CONFIRM_REFUND.getActionName());
		request.setParam("refundOrderItemDTO", refundOrderItemDTO);
		request.setParam("appKey", appkey);
		Response response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<List<OrderDTO>> queryRefundOrder(OrderQTO query, String appkey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_REFUND_ORDER.getActionName());
		request.setParam("orderQTO", query);
		request.setParam("appKey", appkey);
		Response response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<?> getItemRefundDetail(RefundOrderItemDTO refundOrderItemDTO, String appkey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.GET_ITEM_REFUND_DETAIL.getActionName());
		request.setParam("refundOrderItemDTO", refundOrderItemDTO);
		request.setParam("appKey", appkey);
		Response response = tradeService.execute(request);
		return response;
	}

}
