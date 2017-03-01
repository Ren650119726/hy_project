package com.mockuai.tradecenter.core.service;

import java.util.List;
import java.util.Map;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.domain.OrderTogetherDTO;
import com.mockuai.tradecenter.core.exception.TradeException;

public interface OrderService {

	public Map<Long, List<OrderItemDO>> genOrderItemDOsMap(OrderDTO orderDTO, Map<Long, ItemDTO> itemMap,
			Map<Long, ItemSkuDTO> itemSkuMap, String appKey, Map<Long, ActivityItemDTO> activityItemMap)
					throws TradeException;
	

	public void paySubOrderSuccess(List<OrderDO> orders,String outTradeNo,String appKey);
	
	public TradeResponse<OrderDTO> addOrder(RequestContext context);

}
