package com.mockuai.imagecenter.common.api.action;


import com.mockuai.imagecenter.common.constant.ResponseCode;

public class ImageResponse<T> implements Response<T> {

	private static final long serialVersionUID = 1L;

	private T module;
	private int code;
	private String message;
	private long totalCount;

	public ImageResponse(int code) {
		this.code = code;
	}

	public ImageResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public ImageResponse(ResponseCode responseCode) {
		this(responseCode.getCode(), responseCode.getComment());
	}

	public ImageResponse(ResponseCode responseCode, String message) {
		this.code = responseCode.getCode();
		this.message = message;
	}

	public ImageResponse(T module) {
		this(ResponseCode.SUCCESS);
		this.module = module;
	}

	public ImageResponse(T module, long totalCount) {
		this(ResponseCode.SUCCESS);
		this.module = module;
		this.totalCount = totalCount;
	}

	public T getModule() {
		return this.module;
	}

	public int getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public Boolean isSuccess() {
		return ResponseCode.SUCCESS.getCode() == this.code;
	}

	public void setModule(T module) {
		this.module = module;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
}
