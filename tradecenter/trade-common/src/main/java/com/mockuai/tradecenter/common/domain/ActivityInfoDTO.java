package com.mockuai.tradecenter.common.domain;

import java.util.List;

public class ActivityInfoDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String activityId;
	
	List<OrderItemDTO> itemList;

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public List<OrderItemDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<OrderItemDTO> itemList) {
		this.itemList = itemList;
	}
	
	
}
