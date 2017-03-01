package com.mockuai.tradecenter.core.util;
/**
 * 交易相关配置
 *
 */
public class TradeCoreConfig {
	
	
	private int timeoutCancelMinuteNumber;
	
	private int timeoutDeliveryDay;

	private String  zkRegistryAddress;
	
	private String unipayCertDir;
	
	private String wechatpayCertDir;
	
	private int timeoutAutoRefundDay;


	public int getTimeoutCancelMinuteNumber() {
		return timeoutCancelMinuteNumber;
	}

	public void setTimeoutCancelMinuteNumber(int timeoutCancelMinuteNumber) {
		this.timeoutCancelMinuteNumber = timeoutCancelMinuteNumber;
	}

	public int getTimeoutDeliveryDay() {
		return timeoutDeliveryDay;
	}

	public void setTimeoutDeliveryDay(int timeoutDeliveryDay) {
		this.timeoutDeliveryDay = timeoutDeliveryDay;
	}

	public String getZkRegistryAddress() {
		return zkRegistryAddress;
	}

	public void setZkRegistryAddress(String zkRegistryAddress) {
		this.zkRegistryAddress = zkRegistryAddress;
	}

	public String getUnipayCertDir() {
		return unipayCertDir;
	}

	public void setUnipayCertDir(String unipayCertDir) {
		this.unipayCertDir = unipayCertDir;
	}

	public String getWechatpayCertDir() {
		return wechatpayCertDir;
	}

	public void setWechatpayCertDir(String wechatpayCertDir) {
		this.wechatpayCertDir = wechatpayCertDir;
	}

	public int getTimeoutAutoRefundDay() {
		return timeoutAutoRefundDay;
	}

	public void setTimeoutAutoRefundDay(int timeoutAutoRefundDay) {
		this.timeoutAutoRefundDay = timeoutAutoRefundDay;
	}

	

	

	

	
	
	

}
