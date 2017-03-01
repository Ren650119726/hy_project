package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.Map;

public class WxPaymentDTO implements Serializable {
	private String returnCode;
	private String returnMsg;
	/**
	 * 商户订单流水号
	 */
	private String outerTradeNo;
	private String resultCode;
	private String totalFeeStr;
	private String attach;
	/**
	 * 微信交易流水号
	 */
	private String transactionId;
	private String timeEnd;
	private String sign;
	private Map<String,String> originParamMap;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getOuterTradeNo() {
		return outerTradeNo;
	}

	public void setOuterTradeNo(String outerTradeNo) {
		this.outerTradeNo = outerTradeNo;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getTotalFeeStr() {
		return totalFeeStr;
	}

	public void setTotalFeeStr(String totalFeeStr) {
		this.totalFeeStr = totalFeeStr;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Map<String, String> getOriginParamMap() {
		return originParamMap;
	}

	public void setOriginParamMap(Map<String, String> originParamMap) {
		this.originParamMap = originParamMap;
	}
}
