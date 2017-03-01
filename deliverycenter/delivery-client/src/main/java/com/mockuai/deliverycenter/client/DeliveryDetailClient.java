package com.mockuai.deliverycenter.client;

import java.util.List;

import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.dto.express.DeliveryDetailDTO;
import com.mockuai.deliverycenter.common.qto.express.DeliveryDetailQTO;

public interface DeliveryDetailClient {
	
	/**
	 * 批量写入订单号
	 * @param deliveryDetailDTO
	 * @return
	 */
	Response<Boolean> addDeliveryDetail(Long orderId,Long userId,DeliveryDetailDTO deliveryDetailDTO,String appkey);
	
	Response<List<DeliveryDetailDTO>> queryDeliveryDetail(DeliveryDetailQTO deliveryDetailQTO,String appkey);
	
}
