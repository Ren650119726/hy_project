package com.mockuai.tradecenter.mop.api.domain;

import java.util.List;

public class MopCartActivityInfoDTO {
	
	String activityUid;

	List<MopCartItemDTO> itemList;

	public String getActivityUid() {
		return activityUid;
	}

	public void setActivityUid(String activityUid) {
		this.activityUid = activityUid;
	}

	public List<MopCartItemDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<MopCartItemDTO> itemList) {
		this.itemList = itemList;
	}
	
	
}
