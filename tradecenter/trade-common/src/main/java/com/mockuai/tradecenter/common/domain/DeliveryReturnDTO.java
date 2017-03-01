package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.List;

public class DeliveryReturnDTO  implements Serializable{

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Integer deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReturnUser() {
		return returnUser;
	}

	public void setReturnUser(String returnUser) {
		this.returnUser = returnUser;
	}

	public String getReturnPhone() {
		return returnPhone;
	}

	public void setReturnPhone(String returnPhone) {
		this.returnPhone = returnPhone;
	}

	public String getReturnAddress() {
		return returnAddress;
	}

	public void setReturnAddress(String returnAddress) {
		this.returnAddress = returnAddress;
	}

	public List<OrderItemDTO> getReturnItems() {
		return returnItems;
	}

	public void setReturnItems(List<OrderItemDTO> returnItems) {
		this.returnItems = returnItems;
	}

	private String expressNo;

    private Long orderId;

    private Long userId;

    private String memo;
	
	private Integer deliveryId;

    private String userName;

    private String returnUser;

    private String returnPhone;

    private String returnAddress;
    
    private List<OrderItemDTO> returnItems;
}
