package com.mockuai.itemcenter.mop.api.domain;

/**
 * Created by zengzhangqiang on 5/4/15.
 */
public class ItemUidDTO {
    private long itemId;
    private long sellerId;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }
}
