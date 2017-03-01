package com.mockuai.marketingcenter.common.domain.qto;

import java.io.Serializable;

/**
 * Created by edgar.zr on 7/25/2016.
 */
public class OrderRecordQTO extends PageQTO implements Serializable {
	private Long activityId;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
}