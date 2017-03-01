package com.mockuai.deliverycenter.core.util;

import java.util.List;

import com.mockuai.deliverycenter.common.api.DeliveryResponse;
import com.mockuai.deliverycenter.common.constant.RetCodeEnum;

public class ResponseUtil {
	public static DeliveryResponse getResponse(int code, String message) {
		DeliveryResponse response = new DeliveryResponse(code, message);
		return response;
	}

	public static DeliveryResponse getResponse(RetCodeEnum retCodeEnum) {
		DeliveryResponse response = new DeliveryResponse(retCodeEnum);
		return response;
	}

	public static DeliveryResponse getResponse(Object model) {
		DeliveryResponse response = new DeliveryResponse(RetCodeEnum.SUCCESS,
				model);
		return response;
	}

	public static DeliveryResponse getResponse(List modelList, long totalCount) {
		DeliveryResponse response = new DeliveryResponse(RetCodeEnum.SUCCESS,
				modelList, totalCount);
		return response;
	}
}
