package com.mockuai.tradecenter.common.domain;

public class DailyRequestDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6721720174575758704L;

	String bizCode;
	
	String transDate;

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	
	
	
}
