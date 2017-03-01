package com.mockuai.marketingcenter.mop.api.domain;

/**
 * Created by edgar.zr on 1/14/16.
 */
public class MopValueAddedServiceDTO {
    private String serviceUid;
    private Long servicePrice;
    private String serviceName;

    public String getServiceUid() {
        return serviceUid;
    }

    public void setServiceUid(String serviceUid) {
        this.serviceUid = serviceUid;
    }

    public Long getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Long servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}