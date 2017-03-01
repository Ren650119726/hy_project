package com.mockuai.appcenter.common.constant;

/**
 * Created by linyue on 10/19/16.
 */
public enum  BizCodeEnum {

    HAN_SHU("hanshu","平台业务代码");

    private String code;
    private String desc;
    
	private BizCodeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}
	public String getDesc() {
		return desc;
	}
    
    
}
