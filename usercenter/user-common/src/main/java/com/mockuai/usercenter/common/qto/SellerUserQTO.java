package com.mockuai.usercenter.common.qto;

import java.io.Serializable;

public class SellerUserQTO extends QueryPage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2687481761404959286L;

	private Long userId;
	
	private Long sellerId;
	
	private String userName;
	
	private String mobile;
	
	private String relateStatus;
	
	private String keyword;

	private String bizCode;
	
	

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getRelateStatus() {
		return relateStatus;
	}

	public void setRelateStatus(String relateStatus) {
		this.relateStatus = relateStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
}
