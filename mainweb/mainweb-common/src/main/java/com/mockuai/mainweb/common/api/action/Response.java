package com.mockuai.mainweb.common.api.action;

import java.io.Serializable;

/**
 * @author luliang
 * @param <T>
 */
public interface Response<T> extends Serializable {
	/**
	 * 获得data对象
	 * */
	T getModule();

	/**
	 * 获得返回值
	 * */
	int getCode();

	/**
	 * 获得返回消息
	 * */
	String getMessage();

	/**
	 * 获得记录总数
	 * */
	long getTotalCount();

	/**
	 * 测试请求是否成功
	 * */
	Boolean isSuccess();
}
