package com.mockuai.mainweb.core.util;


import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.common.constant.ResponseCode;

public class ResponseUtil {

	public static <T> MainWebResponse<T> getSuccessResponse(T model) {
		MainWebResponse res = new MainWebResponse(model);
		res.setCode(ResponseCode.SUCCESS.getCode());
		res.setMessage(ResponseCode.SUCCESS.getComment());
		return res;
	}


    public static  MainWebResponse getSuccessResponse() {
        MainWebResponse res = new MainWebResponse(ResponseCode.SUCCESS);
        return res;
    }


	public static <T> MainWebResponse<T> getSuccessResponse(T model, long totalCount) {
		MainWebResponse res = new MainWebResponse(model, totalCount);
		res.setCode(ResponseCode.SUCCESS.getCode());
		res.setMessage(ResponseCode.SUCCESS.getComment());
		return res;
	}

	public static MainWebResponse getErrorResponse(ResponseCode responseCode) {
		return new MainWebResponse(responseCode);
	}


	public static MainWebResponse getErrorResponse(int code, String message) {
		return new MainWebResponse(code, message);
	}

}
