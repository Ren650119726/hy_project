package com.mockuai.usercenter.mop.api.domain;

/**
 * 参数异常包装类
 * @author cwr
 */
public class ParamException extends Exception{
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	private String field;//参数错误的字段
	
	/**
	 * @param field
	 * @param msg 参数的错误信息
	 */
	public ParamException(String field,String msg){
		super(msg);
		this.field = field;
	}
	
}
