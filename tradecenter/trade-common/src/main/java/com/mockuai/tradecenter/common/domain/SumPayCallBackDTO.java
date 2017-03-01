package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.Map;

public class SumPayCallBackDTO implements Serializable {
	private String code;
	private String msg;
	private String paymemo;
	private String paystatus;
	private String paytime;
	private String paytradeno;
	private String sign;
	
	private String signtype;
	
	private String tradeamount;
	
	private String tradeno;
	
	private Map<String,String> originParamMap;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPaymemo() {
		return paymemo;
	}

	public void setPaymemo(String paymemo) {
		this.paymemo = paymemo;
	}

	public String getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}

	public String getPaytime() {
		return paytime;
	}

	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}

	public String getPaytradeno() {
		return paytradeno;
	}

	public void setPaytradeno(String paytradeno) {
		this.paytradeno = paytradeno;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSigntype() {
		return signtype;
	}

	public void setSigntype(String signtype) {
		this.signtype = signtype;
	}

	public String getTradeamount() {
		return tradeamount;
	}

	public void setTradeamount(String tradeamount) {
		this.tradeamount = tradeamount;
	}

	public String getTradeno() {
		return tradeno;
	}

	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
	}

	public Map<String, String> getOriginParamMap() {
		return originParamMap;
	}

	public void setOriginParamMap(Map<String, String> originParamMap) {
		this.originParamMap = originParamMap;
	}
	
	

	
	
	
	
}
