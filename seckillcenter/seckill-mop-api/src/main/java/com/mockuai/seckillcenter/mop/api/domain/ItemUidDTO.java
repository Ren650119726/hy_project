package com.mockuai.seckillcenter.mop.api.domain;

/**
 * Created by edgar.zr on 6/12/2016.
 */
public class ItemUidDTO {
    private Long itemId;
    private Long sellerId;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }
}