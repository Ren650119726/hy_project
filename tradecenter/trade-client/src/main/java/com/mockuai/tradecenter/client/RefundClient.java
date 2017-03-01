package com.mockuai.tradecenter.client;

import java.util.List;

import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;

public interface RefundClient {

	Response<Boolean> auditRefund(RefundOrderItemDTO refundOrderItemDTO, String appkey);

	Response<PaymentUrlDTO> confirmRefund(RefundOrderItemDTO refundOrderItemDTO, String appkey);
	
	Response<List<OrderDTO>> queryRefundOrder(OrderQTO query,String appkey);
	
	Response<?> getItemRefundDetail(RefundOrderItemDTO refundOrderItemDTO,String appkey);

}
