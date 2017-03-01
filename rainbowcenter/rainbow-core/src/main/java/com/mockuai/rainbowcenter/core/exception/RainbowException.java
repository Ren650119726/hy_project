package com.mockuai.rainbowcenter.core.exception;

import com.mockuai.rainbowcenter.common.constant.ResponseCode;

public class RainbowException extends Exception{
	private static final long serialVersionUID = 4065133016321980497L;
	private ResponseCode responseCode;

	public RainbowException(){
		super();
		this.responseCode = ResponseCode.SYS_E_DEFAULT_ERROR;
	}
	public RainbowException(String message){
		super(message);
		this.responseCode = ResponseCode.SYS_E_DEFAULT_ERROR;
	}
	public RainbowException(Throwable cause){
		super(cause);
		this.responseCode = ResponseCode.SYS_E_DEFAULT_ERROR;
	}
	public RainbowException(String message, Throwable cause){
		super(message, cause);
		this.responseCode =  ResponseCode.SYS_E_DEFAULT_ERROR;
	}
	public RainbowException(ResponseCode responseCode){
		super(responseCode.getDesc());
		this.responseCode = responseCode;
	}
	public RainbowException(ResponseCode responseCode, String message){
		super(message);
		this.responseCode = responseCode;
	}
	public RainbowException(ResponseCode responseCode, Throwable cause){
		super(responseCode.getDesc(),cause);
		this.responseCode = responseCode;
	}
	public RainbowException(ResponseCode responseCode, String message, Throwable cause){
		super(message, cause);
		this.responseCode = responseCode;
	}

	public ResponseCode getResponseCode() {
		return responseCode;
	}	
}
