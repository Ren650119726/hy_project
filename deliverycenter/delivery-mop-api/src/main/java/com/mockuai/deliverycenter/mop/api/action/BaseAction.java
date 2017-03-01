package com.mockuai.deliverycenter.mop.api.action;

import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.Action;

public abstract class BaseAction implements Action{
    private DeliveryService deliveryService;

	public DeliveryService getDeliveryService() {
		return deliveryService;
	}

	public void setDeliveryService(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}
	

	public ResponseFormat getResponseFormat() {
		return ResponseFormat.STANDARD;
	}
}
