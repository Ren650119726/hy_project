package com.mockuai.dts.core.util;


import com.mockuai.dts.common.api.action.DtsResponse;
import com.mockuai.dts.common.constant.ResponseCode;

public class ResponseUtil {

	public static <T> DtsResponse<T> getSuccessResponse(T model) {
		DtsResponse res = new DtsResponse(model);
		res.setCode(ResponseCode.SUCCESS.getCode());
		res.setMessage(ResponseCode.SUCCESS.getComment());
		return res;
	}

	public static <T> DtsResponse<T> getSuccessResponse(T model, long totalCount) {
		DtsResponse res = new DtsResponse(model, totalCount);
		res.setCode(ResponseCode.SUCCESS.getCode());
		res.setMessage(ResponseCode.SUCCESS.getComment());
		return res;
	}

	public static DtsResponse getErrorResponse(ResponseCode responseCode) {
		return new DtsResponse(responseCode);
	}


	public static DtsResponse getErrorResponse(int code, String message) {
		return new DtsResponse(code, message);
	}

}
