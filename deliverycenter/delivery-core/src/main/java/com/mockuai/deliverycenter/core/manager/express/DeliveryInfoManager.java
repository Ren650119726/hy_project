package com.mockuai.deliverycenter.core.manager.express;

import java.util.List;

import com.mockuai.deliverycenter.common.dto.express.DeliveryInfoDTO;
import com.mockuai.deliverycenter.common.qto.express.DeliveryInfoQTO;
import com.mockuai.deliverycenter.core.exception.DeliveryException;

public interface DeliveryInfoManager {
		
	public Long addDeliveryInfo(DeliveryInfoDTO deliveryInfoDto);
	
	public int deleteByOrderId(Long orderId,Long userId);
	
	public List<DeliveryInfoDTO> queryDeliveryInfo(DeliveryInfoQTO deliveryInfoQTo);
	
	public Boolean updateDeliveryInfo(DeliveryInfoDTO deliveryInfoDto)throws DeliveryException;
	
	public DeliveryInfoDTO getDeliveryInfo(DeliveryInfoDTO deliveryInfoDto); 
}
