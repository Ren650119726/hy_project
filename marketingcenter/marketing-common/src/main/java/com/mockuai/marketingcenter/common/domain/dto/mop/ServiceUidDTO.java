package com.mockuai.marketingcenter.common.domain.dto.mop;

/**
 * Created by edgar.zr on 12/1/15.
 */
public class ServiceUidDTO {
    Long serviceId;
    Long sellerId;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }
}