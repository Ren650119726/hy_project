package com.mockuai.messagecenter.core.exception;

import com.mockuai.messagecenter.common.constant.ResponseCode;

public class MessageException extends Exception {
	private static final long serialVersionUID = 4065133016321980497L;

	private ResponseCode responseCode;

	public MessageException() {
		super();
		responseCode = ResponseCode.REQUEST_SUCCESS;
	}

	public MessageException(Throwable e) {

	}

	public MessageException(ResponseCode responseCode) {
		super();
		this.responseCode = responseCode;
	}

	public MessageException(ResponseCode responseCode, String message) {
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
