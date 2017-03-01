package com.mockuai.tradecenter.core.exception;

import com.mockuai.tradecenter.common.constant.ResponseCode;

public class TradeException extends Exception{
	private static final long serialVersionUID = 4065133016321980497L;
	private ResponseCode responseCode;
	public TradeException(){
		super();
		this.responseCode = ResponseCode.SYS_E_DEFAULT_ERROR;
	}
	public TradeException(String message){
		super(message);
		this.responseCode = ResponseCode.SYS_E_DEFAULT_ERROR;
	}
	public TradeException(Throwable cause){
		super(cause);
		this.responseCode = ResponseCode.SYS_E_DEFAULT_ERROR;
	}
	public TradeException(String message, Throwable cause){
		super(message, cause);
		this.responseCode =  ResponseCode.SYS_E_DEFAULT_ERROR;
	}
	public TradeException(ResponseCode responseCode){
		super(responseCode.getComment());
		this.responseCode = responseCode;
	}
	public TradeException(ResponseCode responseCode, String message){
		super(message);
		this.responseCode = responseCode;
	}
	public TradeException(ResponseCode responseCode,Throwable cause){
		super(responseCode.getComment(),cause);
		this.responseCode = responseCode;
	}
	public TradeException(ResponseCode responseCode,String message,Throwable cause){
		super(message, cause);
		this.responseCode = responseCode;
	}
	public ResponseCode getResponseCode() {
		return responseCode;
	}	
}
