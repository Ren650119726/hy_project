package com.mockuai.appcenter.core.exception;

import com.mockuai.appcenter.common.constant.ResponseCode;

public class AppException extends Exception{
	private static final long serialVersionUID = 4065133016321980497L;
	private ResponseCode responseCode;

	public AppException(){
		super();
		this.responseCode = ResponseCode.SYS_E_DEFAULT_ERROR;
	}
	public AppException(String message){
		super(message);
		this.responseCode = ResponseCode.SYS_E_DEFAULT_ERROR;
	}
	public AppException(Throwable cause){
		super(cause);
		this.responseCode = ResponseCode.SYS_E_DEFAULT_ERROR;
	}
	public AppException(String message, Throwable cause){
		super(message, cause);
		this.responseCode =  ResponseCode.SYS_E_DEFAULT_ERROR;
	}
	public AppException(ResponseCode responseCode){
		super(responseCode.getComment());
		this.responseCode = responseCode;
	}
	public AppException(ResponseCode responseCode, String message){
		super(message);
		this.responseCode = responseCode;
	}
	public AppException(ResponseCode responseCode, Throwable cause){
		super(responseCode.getComment(),cause);
		this.responseCode = responseCode;
	}
	public AppException(ResponseCode responseCode, String message, Throwable cause){
		super(message, cause);
		this.responseCode = responseCode;
	}
	public ResponseCode getResponseCode() {
		return responseCode;
	}	
}
