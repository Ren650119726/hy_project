package com.mockuai.tradecenter.mop.api.domain;

import java.util.List;


public class MopDeliveryInfoDTO {
    private String deliveryInfoUid;
    private Integer deliveryType;
    private String deliveryCompany;
    private Long deliveryFee;
    private String deliveryCode;
    private List<MopDeliveryDetailDTO> deliveryDetailList;

    public String getDeliveryInfoUid() {
        return this.deliveryInfoUid;
    }

    public void setDeliveryInfoUid(String deliveryInfoUid) {
        this.deliveryInfoUid = deliveryInfoUid;
    }

    public Integer getDeliveryType() {
        return this.deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryCompany() {
        return this.deliveryCompany;
    }

    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public Long getDeliveryFee() {
        return this.deliveryFee;
    }

    public void setDeliveryFee(Long deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getDeliveryCode() {
        return this.deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public List<MopDeliveryDetailDTO> getDeliveryDetailList() {
        return this.deliveryDetailList;
    }

    public void setDeliveryDetailList(List<MopDeliveryDetailDTO> deliveryDetailList) {
        this.deliveryDetailList = deliveryDetailList;
    }
}

/* Location:           /work/tmp/trade-mop-api-0.0.1-20150519.033139-22.jar
 * Qualified Name:     com.mockuai.tradecenter.mop.api.domain.MopDeliveryInfoDTO
 * JD-Core Version:    0.6.2
 */