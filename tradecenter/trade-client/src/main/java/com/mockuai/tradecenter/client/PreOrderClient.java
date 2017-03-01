package com.mockuai.tradecenter.client;

import java.util.List;
import java.util.Map;

import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.DataDTO;
import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.ItemCommentDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDeliveryInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.domain.dataCenter.SalesVolumeDTO;
import com.mockuai.tradecenter.common.domain.message.OrderMessageDTO;

public interface PreOrderClient {
	
	
	Response<OrderDTO> addPreOrder(OrderDTO orderDTO, String appKey);
	
	Response<OrderDTO> queryPreOrder(OrderQTO query,String appKey);
	
	
}
