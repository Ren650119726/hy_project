package com.mockuai.tradecenter.mop.api.domain;

import java.util.List;

public class MopActivityInfoDTO {
	//营销活动uid
	String activityUid;

	//换购活动的条件商品
	List<MopOrderItemDTO> itemList;

	public String getActivityUid() {
		return activityUid;
	}

	public void setActivityUid(String activityUid) {
		this.activityUid = activityUid;
	}

	public List<MopOrderItemDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<MopOrderItemDTO> itemList) {
		this.itemList = itemList;
	}
	
	
}
