package com.mockuai.marketingcenter.common.domain.dto.mop;

import java.io.Serializable;

/**
 * Created by edgar.zr on 1/20/16.
 */
public class MopAddedServiceDTO implements Serializable {

    private String serviceUid;
    private String serviceName;
    private Long servicePrice;

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
}