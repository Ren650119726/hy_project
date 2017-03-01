package com.mockuai.shopcenter.api;

import java.io.Serializable;

public interface Response<T> extends Serializable {
	public T getModule();

	public int getCode();

	public String getMessage();

	public long getTotalCount();
}
