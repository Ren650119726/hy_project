package com.mockuai.deliverycenter.common.api;

import java.io.Serializable;

public interface Response<T> extends Serializable {
	public T getModule();

	public boolean isSuccess();

	public int getCode();

	public String getMessage();

	public long getTotalCount();
}
