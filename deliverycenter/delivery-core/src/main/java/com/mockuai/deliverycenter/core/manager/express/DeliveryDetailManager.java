package com.mockuai.deliverycenter.core.manager.express;

import java.util.List;

import com.mockuai.deliverycenter.common.dto.express.DeliveryDetailDTO;
import com.mockuai.deliverycenter.common.qto.express.DeliveryDetailQTO;
import com.mockuai.deliverycenter.core.exception.DeliveryException;

public interface DeliveryDetailManager {
	
	public Boolean addDeliveryDetail(Long orderId,Long userId,List<DeliveryDetailDTO> detailList)throws DeliveryException;
	
	public int deleteByOrderId(Long orderId,Long userId)throws DeliveryException ;
	
	public List<DeliveryDetailDTO> queryDeliveryDetail(DeliveryDetailQTO deliveryDetailQTO)throws DeliveryException;
	
}
