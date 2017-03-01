package com.mockuai.itemcenter.core.util;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ResponseCode;

public class ResponseUtil {

	public static <T> ItemResponse<T> getSuccessResponse(T model) {
		ItemResponse res = new ItemResponse(model);
		res.setCode(ResponseCode.SUCCESS.getCode());
		res.setMessage(ResponseCode.SUCCESS.getComment());
		return res;
	}

    public static  ItemResponse getSuccessResponse() {
        return new ItemResponse(ResponseCode.SUCCESS);

    }

	public static <T> ItemResponse<T> getSuccessResponse(T model, long totalCount) {
		ItemResponse res = new ItemResponse(model, totalCount);
		res.setCode(ResponseCode.SUCCESS.getCode());
		res.setMessage(ResponseCode.SUCCESS.getComment());
		return res;
	}

	public static ItemResponse getErrorResponse(ResponseCode responseCode) {
		return new ItemResponse(responseCode);
	}


	public static ItemResponse getErrorResponse(ResponseCode responseCode, String message) {
		return new ItemResponse(responseCode, message);
	}

	public static  <T> ItemResponse<T> getErrorResponse(ResponseCode responseCode, String message,T model) {
		ItemResponse res = new ItemResponse(model);
		res.setCode(responseCode.getCode());
		res.setMessage(message);
		return res;
	}

	public static ItemResponse getErrorResponse(int code, String message) {
		return new ItemResponse(code, message);
	}

}
