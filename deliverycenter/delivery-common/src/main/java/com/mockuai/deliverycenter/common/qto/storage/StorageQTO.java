package com.mockuai.deliverycenter.common.qto.storage;

import com.mockuai.deliverycenter.common.qto.BaseQTO;

public class StorageQTO extends BaseQTO implements java.io.Serializable {
	private String name;
	private Integer status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
