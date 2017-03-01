package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;

/**
 * Created by edgar.zr on 7/25/2016.
 */
public class UserCouponDataDTO implements Serializable {
	private Integer usedCount;
	private Integer unusedCount;
	private Integer invalidCount;

	public Integer getUsedCount() {
		return usedCount;
	}

	public void setUsedCount(Integer usedCount) {
		this.usedCount = usedCount;
	}

	public Integer getUnusedCount() {
		return unusedCount;
	}

	public void setUnusedCount(Integer unusedCount) {
		this.unusedCount = unusedCount;
	}

	public Integer getInvalidCount() {
		return invalidCount;
	}

	public void setInvalidCount(Integer invalidCount) {
		this.invalidCount = invalidCount;
	}
}