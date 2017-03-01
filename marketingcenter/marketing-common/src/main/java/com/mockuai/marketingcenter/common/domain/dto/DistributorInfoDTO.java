package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;

/**
 * Created by edgar.zr on 5/23/2016.
 */
public class DistributorInfoDTO implements Serializable {
    private Long distributorId;
    private String shopName;
    private String distributorSign;

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDistributorSign() {
        return distributorSign;
    }

    public void setDistributorSign(String distributorSign) {
        this.distributorSign = distributorSign;
    }
}