package com.mockuai.deliverycenter.common.qto.express;

import java.io.Serializable;

import com.mockuai.deliverycenter.common.qto.BaseQTO;

public class DeliveryDetailQTO extends BaseQTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3257841222537197234L;
	
	private Long id;
	
	private Long orderId;
	
	private Long userId;
	
	private String deliveryCode;
	
	private String deliveryCompanyCode;//快递公司 code
	
	
	
	public String getDeliveryCompanyCode() {
		return deliveryCompanyCode;
	}

	public void setDeliveryCompanyCode(String deliveryCompanyCode) {
		this.deliveryCompanyCode = deliveryCompanyCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}
	
}
