package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;

/**
 * 用来统计。。。。
 * @author hzmk
 *
 */
public class OrderViewDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4040585574830731426L;

	private String deviceType;
	
	private String ip;

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	

}
