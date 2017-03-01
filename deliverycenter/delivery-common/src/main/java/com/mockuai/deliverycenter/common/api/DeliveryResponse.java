package com.mockuai.deliverycenter.common.api;

import com.mockuai.deliverycenter.common.constant.RetCodeEnum;

public class DeliveryResponse<T> implements Response<T> {
	private static final long serialVersionUID = 8295766534182699773L;

	private T module;
	private int code;
	private String message;
	private long totalCount = 0;

	public DeliveryResponse() {

	}

	public DeliveryResponse(RetCodeEnum retCodeEnum) {
		this.code = retCodeEnum.getCode();
		this.message = retCodeEnum.getMessage();
	}

	public DeliveryResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public DeliveryResponse(RetCodeEnum retCodeEnum, T module) {
		this.code = retCodeEnum.getCode();
		this.message = retCodeEnum.getMessage();
		this.module = module;
	}

	public DeliveryResponse(RetCodeEnum retCodeEnum, T module, long totalCount) {
		this.code = retCodeEnum.getCode();
		this.message = retCodeEnum.getMessage();
		this.module = module;
		this.totalCount = totalCount;
	}

	public T getModule() {
		// TODO Auto-generated method stub
		return this.module;
	}

	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return RetCodeEnum.SUCCESS.getCode() == (this.code) ? true : false;
	}

	public int getCode() {
		// TODO Auto-generated method stub
		return this.code;
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return this.message;
	}

	public long getTotalCount() {
		// TODO Auto-generated method stub
		return this.totalCount;
	}

}
