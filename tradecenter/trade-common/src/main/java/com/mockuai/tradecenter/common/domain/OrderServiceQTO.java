package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;

public class OrderServiceQTO extends BaseQTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2327606512438246521L;

	/**
	 * 订单ID
	 */
	private Long orderId;
	

	/**
	 * 卖家ID
	 */
	private Long sellerId;
	
	private Long itemSkuId;
	
	private Long orderItemId;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}



	
	
	
	
}
