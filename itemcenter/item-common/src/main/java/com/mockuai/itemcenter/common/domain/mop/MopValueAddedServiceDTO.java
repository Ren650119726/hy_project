package com.mockuai.itemcenter.common.domain.mop;

import java.io.Serializable;

/**
 * Created by yindingyu on 15/12/23.
 */
public class MopValueAddedServiceDTO  implements Serializable{

    private String serviceUid;

    private String serviceName;

    private String serviceDesc;

    private String iconUrl;

    private long servicePrice;


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

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public long getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(long servicePrice) {
        this.servicePrice = servicePrice;
    }
}
