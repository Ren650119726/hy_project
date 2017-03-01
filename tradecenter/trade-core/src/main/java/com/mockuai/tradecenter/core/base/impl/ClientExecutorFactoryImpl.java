package com.mockuai.tradecenter.core.base.impl;

import java.util.Map;

import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.base.ClientExecutorFactory;

/**
 *
 */
public class ClientExecutorFactoryImpl implements ClientExecutorFactory {

	/**
	 * 根据不同的paymentId 取不同的执行器
	 */
	private Map<String, ClientExecutor> executorsMap;

	@Override
	public ClientExecutor getExecutor(String paymentId) {
		return executorsMap.get(paymentId);
	}

	public void setExecutorsMap(Map<String, ClientExecutor> executorsMap) {
		this.executorsMap = executorsMap;
	}

}