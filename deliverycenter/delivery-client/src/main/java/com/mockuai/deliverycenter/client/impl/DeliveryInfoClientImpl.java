package com.mockuai.deliverycenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.deliverycenter.client.DeliveryInfoClient;
import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.express.DeliveryInfoDTO;
import com.mockuai.deliverycenter.common.qto.express.DeliveryInfoQTO;

public class DeliveryInfoClientImpl implements DeliveryInfoClient {
	
	@Resource
	private DeliveryService deliveryService;
	
	public Response<DeliveryInfoDTO> addDeliveryInfo(
			DeliveryInfoDTO deliveryInfoDTO,String appkey) {
		
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_DELIVERY_INFO.getActionName());
		request.setParam("deliveryInfoDTO", deliveryInfoDTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<List<DeliveryInfoDTO>> queryDeliveryInfo(DeliveryInfoQTO deliveryInfoQTO,String appkey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_DELIVERY_INFO.getActionName());
		request.setParam("deliveryInfoQTO", deliveryInfoQTO);
		request.setParam("appKey", appkey);
		
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<Boolean> batchAddDeliveryInfo(List<DeliveryInfoDTO> list, String appkey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.BATCH_ADD_DELIVERY_INFO.getActionName());
		request.setParam("deliveryInfoDTOList", list);
		request.setParam("appKey", appkey);
		
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<Boolean> updateDeliveryInfo(DeliveryInfoDTO deliveryDTO, String appkey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPATE_DELIVERY_INFO.getActionName());
		request.setParam("deliveryInfoDTO", deliveryDTO);
		request.setParam("appKey", appkey);
		Response response = deliveryService.execute(request);
		return response;
	}
	public Response<DeliveryInfoDTO> getDeliveryInfoDTO(DeliveryInfoDTO deliveryDTO,String appkey){
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.GET_DELIVERY_INFO.getActionName());
		request.setParam("deliveryInfoDTO", deliveryDTO);
		request.setParam("appKey", appkey);
		Response response = deliveryService.execute(request);
		return response;
	}
}
