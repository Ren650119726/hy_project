package com.mockuai.itemcenter.mop.api.domain;

/**
 * Created by zengzhangqiang on 4/27/15.
 */
public class SkuUidDTO {
    private Long skuId;
    private Long sellerId;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }
}
