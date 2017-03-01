package com.mockuai.dts.common.api.action;

import java.io.Serializable;

/**
 * @author luliang
 * @param <T>
 */
public interface Response<T> extends Serializable {
	public T getModule();

	public int getCode();

	public String getMessage();

	public long getTotalCount();
}
