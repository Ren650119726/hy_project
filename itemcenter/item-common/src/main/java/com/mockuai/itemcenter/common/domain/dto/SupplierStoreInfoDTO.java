package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;

/**
 * Created by yindingyu on 16/5/19.
 */
public class SupplierStoreInfoDTO implements Serializable{

    private Long storeId;
    private Long sellerId;
    private Long supplierId;
    private Long stockNum;
    private String bizCode;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }
}
