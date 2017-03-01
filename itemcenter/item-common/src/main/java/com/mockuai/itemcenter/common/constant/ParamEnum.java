package com.mockuai.itemcenter.common.constant;

public enum ParamEnum {
	SYS_APP_CODE("appCode"),
	SYS_APP_PASSWORD("appPwd"),
	SYS_ACTION("action"),

	SELLER_ID("1841254");

	private String value;
	private ParamEnum(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
