package com.mockuai.itemcenter.mop.api.domain;

/**
 * Created by yindingyu on 16/1/14.
 */
public class ItemGroupUidDTO {

    private long sellerId;

    private long groupId;

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}
