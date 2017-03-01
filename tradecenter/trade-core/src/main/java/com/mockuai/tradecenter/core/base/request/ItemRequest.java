package com.mockuai.tradecenter.core.base.request;

import java.util.List;

import com.mockuai.tradecenter.common.domain.OrderItemDTO;

public class ItemRequest {
	
	List<OrderItemDTO> orderItemList;

	public List<OrderItemDTO> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItemDTO> orderItemList) {
		this.orderItemList = orderItemList;
	}
	

	
}
