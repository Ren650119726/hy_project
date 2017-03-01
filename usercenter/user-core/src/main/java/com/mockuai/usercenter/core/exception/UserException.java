package com.mockuai.usercenter.core.exception;

import com.mockuai.usercenter.common.constant.ResponseCode;

public class UserException extends Exception {
	private static final long serialVersionUID = 4065133016321980497L;

	private ResponseCode responseCode;

	public UserException() {
		super();
		responseCode = ResponseCode.REQUEST_SUCCESS;
	}

	public UserException(Throwable e) {

	}

	public UserException(ResponseCode responseCode) {
		super();
		this.responseCode = responseCode;
	}

	public UserException(ResponseCode responseCode, String message) {
		super(message);
		this.responseCode = responseCode;
	}

	public ResponseCode getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}

}
