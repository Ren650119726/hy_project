package com.mockuai.deliverycenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.deliverycenter.client.DeliveryDetailClient;
import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.express.DeliveryDetailDTO;
import com.mockuai.deliverycenter.common.qto.express.DeliveryDetailQTO;

public class DeliveryDetailClientImpl implements DeliveryDetailClient {

	@Resource
	private DeliveryService deliveryService;
	
	public Response<Boolean> addDeliveryDetail(Long orderId,Long userId,
			DeliveryDetailDTO deliveryDetailDTO,String appkey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_DELIVERY_DETAIL.getActionName());
		request.setParam("orderId",orderId);
		request.setParam("userId",userId);
		request.setParam("deliveryDetailList",deliveryDetailDTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<List<DeliveryDetailDTO>> queryDeliveryDetail(DeliveryDetailQTO deliveryDetailQTO,String appkey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_DELIVERY_DETAIL.getActionName());
		request.setParam("deliveryDetailQTO", deliveryDetailQTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}
}
