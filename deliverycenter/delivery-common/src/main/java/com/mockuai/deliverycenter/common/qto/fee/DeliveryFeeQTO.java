package com.mockuai.deliverycenter.common.qto.fee;

import com.mockuai.deliverycenter.common.qto.BaseQTO;

public class DeliveryFeeQTO extends BaseQTO implements java.io.Serializable {
	private Long regionId;
	private Long weight;

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

}
