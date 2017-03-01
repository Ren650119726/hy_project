package com.mockuai.shopcenter.mop.api.domain;

/**
 * Created by ziqi
 * sellerId_shopId
 */
public class ShopUidDTO {
    private long shopId;
    private long sellerId;


    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }
}
