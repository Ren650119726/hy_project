package com.mockuai.itemcenter.common.api;

import com.mockuai.itemcenter.common.constant.ResponseCode;

public class ItemResponse<T> implements Response<T> {

	private static final long serialVersionUID = 8295766534182699773L;

	private T module;
	private int code;
	private String message;
	private long totalCount = 0;


	public ItemResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public ItemResponse(ResponseCode responseCode) {
		this.code = responseCode.getCode();
		this.message = responseCode.getComment();
	}

	public ItemResponse(ResponseCode responseCode, String message) {
		this.code = responseCode.getCode();
		this.message = message;
	}

	public ItemResponse(ResponseCode responseCode, String message,T module) {
		this.code = responseCode.getCode();
		this.message = message;
		this.setModule(module);
	}

	public ItemResponse(T module) {
		this.code = ResponseCode.SUCCESS.getCode();
		this.module = module;
		totalCount = 1;
	}

	public ItemResponse(T module, long totalCount) {
		this.code = ResponseCode.SUCCESS.getCode();
		this.module = module;
		this.totalCount = totalCount;
	}

	public boolean isSuccess() {
		return ResponseCode.SUCCESS.getCode() == this.code;
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
