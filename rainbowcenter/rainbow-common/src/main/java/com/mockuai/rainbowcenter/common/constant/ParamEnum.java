package com.mockuai.rainbowcenter.common.constant;

public enum ParamEnum {
	SYS_APP_CODE("appCode"),
	SYS_APP_PASSWORD("appPwd"),
	SYS_ACTION("action"),
	SELLER_ID("1841254"),
	SUPPLIER_APP_KEY("5e011b6bbea2dd6774d0d34f6cb6535d");  //5e011b6bbea2dd6774d0d34f6cb6535d 5b036edd2fe8730db1983368a122fb45 8f83bfb394f215276e20e826a99a002b
	private String value;
	private ParamEnum(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
