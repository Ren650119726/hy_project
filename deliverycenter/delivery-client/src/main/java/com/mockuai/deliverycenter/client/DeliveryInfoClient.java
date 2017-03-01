package com.mockuai.deliverycenter.client;

import java.util.List;

import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.dto.express.DeliveryInfoDTO;
import com.mockuai.deliverycenter.common.qto.express.DeliveryInfoQTO;

public interface DeliveryInfoClient {
	
	public Response<DeliveryInfoDTO> addDeliveryInfo(DeliveryInfoDTO DeliveryInfoDTO,String appkey);
	
	public Response<List<DeliveryInfoDTO>> queryDeliveryInfo(DeliveryInfoQTO deliveryInfoQTO,String appkey);
	
	public Response<Boolean> batchAddDeliveryInfo(List<DeliveryInfoDTO> list,String appkey);
	
	public Response<Boolean> updateDeliveryInfo(DeliveryInfoDTO deliveryDTO,String appkey);
	
	public Response<DeliveryInfoDTO> getDeliveryInfoDTO(DeliveryInfoDTO deliveryDTO,String appkey);
	
}
