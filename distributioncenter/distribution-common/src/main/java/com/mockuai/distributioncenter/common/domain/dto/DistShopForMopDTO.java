package com.mockuai.distributioncenter.common.domain.dto;

import java.io.Serializable;

/**
 * Created by duke on 16/5/26.
 */
public class DistShopForMopDTO implements Serializable {
    private Long distributorId;
    private String shopName;
    private String distributorSign;
    private String headImgUrl;

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

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
