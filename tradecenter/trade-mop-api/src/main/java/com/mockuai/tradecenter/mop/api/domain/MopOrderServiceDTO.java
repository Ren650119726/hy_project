package com.mockuai.tradecenter.mop.api.domain;

public class MopOrderServiceDTO {

	private String serviceUid;
	private String serviceName;
	private Long servicePrice;
	private String serviceImageUrl;

	public String getServiceUid() {
		return serviceUid;
	}

	public void setServiceUid(String serviceUid) {
		this.serviceUid = serviceUid;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Long getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(Long servicePrice) {
		this.servicePrice = servicePrice;
	}

	public String getServiceImageUrl() {
		return serviceImageUrl;
	}

	public void setServiceImageUrl(String serviceImageUrl) {
		this.serviceImageUrl = serviceImageUrl;
	}

}
