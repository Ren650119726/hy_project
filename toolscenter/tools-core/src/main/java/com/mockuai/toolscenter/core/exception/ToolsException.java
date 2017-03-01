package com.mockuai.toolscenter.core.exception;

import com.mockuai.toolscenter.common.constant.ResponseCode;

public class ToolsException extends Exception{
	private static final long serialVersionUID = 4065133016321980497L;
	private ResponseCode responseCode;

	public ToolsException(){
		super();
		this.responseCode = ResponseCode.SYS_E_DEFAULT_ERROR;
	}
	public ToolsException(String message){
		super(message);
		this.responseCode = ResponseCode.SYS_E_DEFAULT_ERROR;
	}
	public ToolsException(Throwable cause){
		super(cause);
		this.responseCode = ResponseCode.SYS_E_DEFAULT_ERROR;
	}
	public ToolsException(String message, Throwable cause){
		super(message, cause);
		this.responseCode =  ResponseCode.SYS_E_DEFAULT_ERROR;
	}
	public ToolsException(ResponseCode responseCode){
		super(responseCode.getComment());
		this.responseCode = responseCode;
	}
	public ToolsException(ResponseCode responseCode, String message){
		super(message);
		this.responseCode = responseCode;
	}
	public ToolsException(ResponseCode responseCode, Throwable cause){
		super(responseCode.getComment(),cause);
		this.responseCode = responseCode;
	}
	public ToolsException(ResponseCode responseCode, String message, Throwable cause){
		super(message, cause);
		this.responseCode = responseCode;
	}
	public ResponseCode getResponseCode() {
		return responseCode;
	}	
}
