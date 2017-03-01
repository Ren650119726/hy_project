package com.mockuai.tradecenter.core.base.request;

import java.util.List;
import java.util.Map;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderViewDTO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;

public class OrderRequest {

	OrderDTO orderDTO;

	Map<Long, ItemSkuDTO> itemSkuMap;

	Map<Long, ItemDTO> itemMap;
	
	List<OrderDiscountInfoDO> orderDiscountInfoDOList;
	
	Long deliveryFee;
	
	List<OrderItemDO> orderItemList;

	OrderViewDTO orderViewDTO;
	
	public Map<Long, ItemSkuDTO> getItemSkuMap() {
		return itemSkuMap;
	}

	public void setItemSkuMap(Map<Long, ItemSkuDTO> itemSkuMap) {
		this.itemSkuMap = itemSkuMap;
	}

	public Map<Long, ItemDTO> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<Long, ItemDTO> itemMap) {
		this.itemMap = itemMap;
	}

	public OrderDTO getOrderDTO() {
		return orderDTO;
	}

	public void setOrderDTO(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}

	public List<OrderDiscountInfoDO> getOrderDiscountInfoDOList() {
		return orderDiscountInfoDOList;
	}

	public void setOrderDiscountInfoDOList(List<OrderDiscountInfoDO> orderDiscountInfoDOList) {
		this.orderDiscountInfoDOList = orderDiscountInfoDOList;
	}

	public Long getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(Long deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public List<OrderItemDO> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItemDO> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public OrderViewDTO getOrderViewDTO() {
		return orderViewDTO;
	}

	public void setOrderViewDTO(OrderViewDTO orderViewDTO) {
		this.orderViewDTO = orderViewDTO;
	}
	
	

}
