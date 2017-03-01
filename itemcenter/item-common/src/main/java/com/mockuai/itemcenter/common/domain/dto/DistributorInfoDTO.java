package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;

/**
 * Created by yindingyu on 16/5/23.
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
