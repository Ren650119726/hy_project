package com.mockuai.dts.common.api.action;


import com.mockuai.dts.common.constant.ResponseCode;

public class DtsResponse<T> implements Response<T> {

	private static final long serialVersionUID = 1L;
	private T module;
	private int code;
	private String message;
	private long totalCount = 0;


	public DtsResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public DtsResponse(ResponseCode responseCode) {
		this.code = responseCode.getCode();
		this.message = responseCode.getComment();
	}

	public DtsResponse(ResponseCode responseCode, String message) {
		this.code = responseCode.getCode();
		this.message = message;
	}

	public DtsResponse(T module) {
		this.code = ResponseCode.SUCCESS.getCode();
		this.module = module;
		totalCount = 1;
	}

	public DtsResponse(T module, long totalCount) {
		this.code = ResponseCode.SUCCESS.getCode();
		this.module = module;
		this.totalCount = totalCount;
	}

	public T getModule() {
		return module;
	}

	public void setModule(T module) {
		this.module = module;
	}

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

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
}
