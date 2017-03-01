package com.mockuai.tradecenter.common.domain.message;

import com.mockuai.tradecenter.common.domain.BaseDTO;

public class OrderMessageDTO extends BaseDTO{

	private Long orderId;
	
	private Long userId;
	
	private String message;

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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
