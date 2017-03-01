package com.mockuai.tradecenter.core.service;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;

public class ResponseUtils {

	/**
	 * 返回不带任何data的处理成功的response
	 * @return
	 */
	public static TradeResponse getSuccessResponse() {
		return getSuccessResponse(null);
	}

	/**
	 * 返回成功的TradeResponse 赋值成功的编码和描述信息
	 * @param module
	 * @return
	 */
	public static <T> TradeResponse<T> getSuccessResponse(T module){
		TradeResponse<T> tradeResponse = new TradeResponse<T>(module);
		// 填充成功的编码和描述信息
		tradeResponse.setCode(ResponseCode.RESPONSE_SUCCESS.getCode()); 
		tradeResponse.setMessage(ResponseCode.RESPONSE_SUCCESS.getComment());
		return tradeResponse;
	}


	
	/**
	 * 返回处理失败的TradeResponse 赋值错误编码和错误描述
	 * @param responseCode
	 * @return
	 */
	public static TradeResponse getFailResponse(ResponseCode responseCode){
		TradeResponse tradeResponse = new TradeResponse(responseCode.getCode(),responseCode.getComment());
		return tradeResponse;
	}
	
	/**
	 * 
	 * @param responseCode
	 * @return
	 */
	public static TradeResponse getFailResponse(ResponseCode responseCode,String message){
		TradeResponse tradeResponse = new TradeResponse(responseCode.getCode(),message);
		return tradeResponse;
	} 
	
}
