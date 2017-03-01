package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.List;

public class SalesTotalDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5846776373538324380L;

	private List<SalesRatioDTO> salesRatioList;
	
	private Long totalAmount;//销售数量
	
	private Integer salesVolumes;//销售数量

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<SalesRatioDTO> getSalesRatioList() {
		return salesRatioList;
	}

	public void setSalesRatioList(List<SalesRatioDTO> salesRatioList) {
		this.salesRatioList = salesRatioList;
	}

	public Integer getSalesVolumes() {
		return salesVolumes;
	}

	public void setSalesVolumes(Integer salesVolumes) {
		this.salesVolumes = salesVolumes;
	}

	
	

}
