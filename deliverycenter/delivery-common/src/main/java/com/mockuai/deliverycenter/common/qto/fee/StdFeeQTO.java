package com.mockuai.deliverycenter.common.qto.fee;

import com.mockuai.deliverycenter.common.qto.BaseQTO;

public class StdFeeQTO extends BaseQTO implements java.io.Serializable {
	private String name;
	private String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
