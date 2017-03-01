package com.mockuai.appcenter.core.service;


import com.mockuai.appcenter.common.api.AppResponse;
import com.mockuai.appcenter.common.constant.ResponseCode;

public class ResponseUtils {
	/**
	 * 返回成功的TradeResponse 赋值成功的编码和描述信息
	 * @param responseCode
	 * @param module
	 * @return
	 */
	public static <T> AppResponse<T> getSuccessResponse(T module){
		AppResponse<T> appResponse = new AppResponse<T>(module);
		// 填充成功的编码和描述信息
		appResponse.setCode(ResponseCode.RESPONSE_SUCCESS.getCode());
		appResponse.setMessage(ResponseCode.RESPONSE_SUCCESS.getComment());
		return appResponse;
	}
	
	/**
	 * 返回处理失败的TradeResponse 赋值错误编码和错误描述
	 * @param responseCode
	 * @return
	 */
	public static AppResponse getFailResponse(ResponseCode responseCode){
		AppResponse appResponse = new AppResponse(responseCode.getCode(),responseCode.getComment());
		return appResponse;
	}
	
	/**
	 * 
	 * @param responseCode
	 * @return
	 */
	public static AppResponse getFailResponse(ResponseCode responseCode,String message){
		AppResponse appResponse = new AppResponse(responseCode.getCode(),message);
		return appResponse;
	} 
	
}
