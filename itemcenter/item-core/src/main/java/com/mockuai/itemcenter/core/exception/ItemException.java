package com.mockuai.itemcenter.core.exception;

import com.mockuai.itemcenter.common.constant.ResponseCode;

public class ItemException extends Exception {
	private static final long serialVersionUID = 4065133016321980497L;
	private int code;
	private String message;
	private ResponseCode responseCode;

	public ItemException() {
		super();
		this.code = ResponseCode.SYS_E_DEFAULT_ERROR.getCode();
	}

	public ItemException(String message) {
		super(message);
		this.code = ResponseCode.SYS_E_DEFAULT_ERROR.getCode();
	}

	public ItemException(Throwable cause) {
		super(cause);
		this.code = ResponseCode.SYS_E_DEFAULT_ERROR.getCode();
	}

	public ItemException(String message, Throwable cause) {
		super(message, cause);
		this.code = ResponseCode.SYS_E_DEFAULT_ERROR.getCode();
	}

	public ItemException(int code, String message) {
		super(message);
		this.code = code;
	}

	public ItemException(ResponseCode responseCode, Throwable cause) {
		super(responseCode.getComment(), cause);
		this.code = responseCode.getCode();
		this.responseCode = responseCode;
	}

	public ItemException(ResponseCode responseCode, String message) {
		this.message = message;
		this.code = responseCode.getCode();
		this.responseCode = responseCode;
	}
    public ItemException(ResponseCode responseCode) {
		this.message = responseCode.getComment();
		this.code = responseCode.getCode();
		this.responseCode = responseCode;
	}

	public ItemException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResponseCode getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}
}
