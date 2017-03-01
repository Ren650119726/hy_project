package com.mockuai.tradecenter.core.exception;

/**
 * 用于包装服务调用返回的异常
 * @author cwr
 */
public class ServiceException extends Exception{
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private int code;
	
	private String message;
	
	public ServiceException(int code,String message){
		super();
		this.code =code;
		this.message =message;
	}
	
}
