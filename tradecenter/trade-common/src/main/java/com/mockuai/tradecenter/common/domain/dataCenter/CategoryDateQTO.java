package com.mockuai.tradecenter.common.domain.dataCenter;

import com.mockuai.tradecenter.common.domain.BaseQTO;

public class CategoryDateQTO extends BaseQTO{

	private Long categoryId;
	
	private Integer topItemNumber;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getTopItemNumber() {
		return topItemNumber;
	}

	public void setTopItemNumber(Integer topItemNumber) {
		this.topItemNumber = topItemNumber;
	}
	
	
	
	
	
}
