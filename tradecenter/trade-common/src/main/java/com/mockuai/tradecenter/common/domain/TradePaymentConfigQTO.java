package com.mockuai.tradecenter.common.domain;

public class TradePaymentConfigQTO extends BaseQTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1209413908809240308L;

	String bizCode;
	
	Integer status;
	
	Integer sort;
	
    Integer deleteMark;

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}

}
