package com.mockuai.itemcenter.core.util;

/**
 * Created by yindingyu on 16/6/2.
 */
public class ItemUidDTO {
    private long sellerId;
    private long itemId;


    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getItemId() {
        return itemId;
    }
}
