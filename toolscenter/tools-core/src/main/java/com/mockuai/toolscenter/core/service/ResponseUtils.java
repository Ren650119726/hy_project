package com.mockuai.toolscenter.core.service;


import com.mockuai.toolscenter.common.api.ToolsResponse;
import com.mockuai.toolscenter.common.constant.ResponseCode;

public class ResponseUtils {
	/**
	 * 返回成功的TradeResponse 赋值成功的编码和描述信息
	 * @param responseCode
	 * @param module
	 * @return
	 */
	public static <T> ToolsResponse<T> getSuccessResponse(T module){
		ToolsResponse<T> appResponse = new ToolsResponse<T>(module);
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
	public static ToolsResponse getFailResponse(ResponseCode responseCode){
		ToolsResponse appResponse = new ToolsResponse(responseCode.getCode(),responseCode.getComment());
		return appResponse;
	}
	
	/**
	 * 
	 * @param responseCode
	 * @return
	 */
	public static ToolsResponse getFailResponse(ResponseCode responseCode, String message){
		ToolsResponse appResponse = new ToolsResponse(responseCode.getCode(),message);
		return appResponse;
	} 
	
}
