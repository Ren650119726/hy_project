package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;

public class BizLockQTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4509975030659155657L;
	String lockSn;
	Integer type;
	public String getLockSn() {
		return lockSn;
	}
	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
	
	
}
