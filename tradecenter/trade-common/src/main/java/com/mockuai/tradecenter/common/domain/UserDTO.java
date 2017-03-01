package com.mockuai.tradecenter.common.domain;

public class UserDTO {
	private String address;
	private Long userId;
	private Integer province;
	private Integer city;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	private Long addressId;
	
}	
