package com.mockuai.tradecenter.core.domain;

import java.io.Serializable;

public class PaymentDeclareDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6514669681020064090L;

	private String mchCustomsCode;
	
	private String mchCustomsName;
	
	private String customsPlace;

	public String getMchCustomsCode() {
		return mchCustomsCode;
	}

	public void setMchCustomsCode(String mchCustomsCode) {
		this.mchCustomsCode = mchCustomsCode;
	}

	public String getMchCustomsName() {
		return mchCustomsName;
	}

	public void setMchCustomsName(String mchCustomsName) {
		this.mchCustomsName = mchCustomsName;
	}

	public String getCustomsPlace() {
		return customsPlace;
	}

	public void setCustomsPlace(String customsPlace) {
		this.customsPlace = customsPlace;
	}

	
	
	
	
}
