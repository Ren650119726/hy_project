package com.mockuai.tradecenter.common.domain;


public class TradePaymentConfigDTO extends BaseDTO {

	private static final long serialVersionUID = -3869731454982055368L;

	String paymentKey;
	
	String paymentName;
	
	Integer sort;

	public String getPaymentKey() {
		return paymentKey;
	}

	public void setPaymentKey(String paymentKey) {
		this.paymentKey = paymentKey;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	

	
    
}
