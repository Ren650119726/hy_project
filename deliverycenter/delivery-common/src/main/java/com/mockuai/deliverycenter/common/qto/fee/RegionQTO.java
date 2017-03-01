package com.mockuai.deliverycenter.common.qto.fee;

import com.mockuai.deliverycenter.common.qto.BaseQTO;

import java.util.List;

public class RegionQTO extends BaseQTO implements java.io.Serializable {
	private List<String> regionCodes;
	private String parentCode;
	private String name;
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getRegionCodes() {
		return regionCodes;
	}

	public void setRegionCodes(List<String> regionCodes) {
		this.regionCodes = regionCodes;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
}
