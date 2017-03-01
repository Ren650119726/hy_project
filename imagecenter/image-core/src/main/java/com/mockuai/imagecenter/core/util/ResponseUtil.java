package com.mockuai.imagecenter.core.util;


import com.mockuai.imagecenter.common.api.action.ImageResponse;
import com.mockuai.imagecenter.common.constant.ResponseCode;

public class ResponseUtil {

	public static <T> ImageResponse<T> getSuccessResponse(T model) {
		ImageResponse res = new ImageResponse(model);
		res.setCode(ResponseCode.SUCCESS.getCode());
		res.setMessage(ResponseCode.SUCCESS.getComment());
		return res;
	}

	public static <T> ImageResponse<T> getSuccessResponse(T model, long totalCount) {
		ImageResponse res = new ImageResponse(model, totalCount);
		res.setCode(ResponseCode.SUCCESS.getCode());
		res.setMessage(ResponseCode.SUCCESS.getComment());
		return res;
	}

	public static ImageResponse getErrorResponse(ResponseCode responseCode) {
		return new ImageResponse(responseCode);
	}


	public static ImageResponse getErrorResponse(int code, String message) {
		return new ImageResponse(code, message);
	}

}
