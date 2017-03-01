package com.mockuai.deliverycenter.core.util;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mockuai.deliverycenter.core.domain.ThirdDeliveryInfoResult;

public class DeliveryInstance {

	private static DeliveryInstance realDataInstance = new DeliveryInstance();

	private ExecutorService sendCacheExec = Executors.newCachedThreadPool();

	private CompletionService<ThirdDeliveryInfoResult> ecs = new ExecutorCompletionService<ThirdDeliveryInfoResult>(
			sendCacheExec);

	private DeliveryInstance() {
	}

	public static DeliveryInstance getDeliveryInstance() {
		return realDataInstance;
	}

	public CompletionService<ThirdDeliveryInfoResult> getEcs() {
		return this.ecs;
	}

	public void setEcs(CompletionService<ThirdDeliveryInfoResult> ecs) {
		this.ecs = ecs;
	}
}
