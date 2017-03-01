package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;

/**
 * Created by zengzhangqiang on 1/5/16.
 */
public class HigoExtraInfoDTO implements Serializable {
    //原始税费
    private Long originalTaxFee;
    //最终税费
    private Long finalTaxFee;
    //税率
    private Float taxRate;
    //货源地
    private String supplyBase;
    //发货方式（保税区发货、海外直邮）
    private Integer deliveryType;
    //税号
    private String taxNumber;

    public Long getOriginalTaxFee() {
        return originalTaxFee;
    }

    public void setOriginalTaxFee(Long originalTaxFee) {
        this.originalTaxFee = originalTaxFee;
    }

    public Long getFinalTaxFee() {
        return finalTaxFee;
    }

    public void setFinalTaxFee(Long finalTaxFee) {
        this.finalTaxFee = finalTaxFee;
    }

    public Float getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Float taxRate) {
        this.taxRate = taxRate;
    }

    public String getSupplyBase() {
        return supplyBase;
    }

    public void setSupplyBase(String supplyBase) {
        this.supplyBase = supplyBase;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }
}