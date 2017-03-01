package com.mockuai.distributioncenter.mop.api.domain;

/**
 * Created by duke on 16/5/21.
 */
public class MopCollectionShopDTO {
    private Long id;
    private Long shopId;
    private String shopName;
    private String shopDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }
}
