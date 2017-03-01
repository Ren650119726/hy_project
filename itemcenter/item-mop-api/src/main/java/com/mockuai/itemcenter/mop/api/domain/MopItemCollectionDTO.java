package com.mockuai.itemcenter.mop.api.domain;

/**
 * Created by zengzhangqiang on 5/27/15.
 */
public class MopItemCollectionDTO {

    private String itemUid;

    private Long distributorId;

    private Long shareUserId;

    public Long getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(Long shareUserId) {
        this.shareUserId = shareUserId;
    }

    public String getItemUid() {
        return itemUid;
    }

    public void setItemUid(String itemUid) {
        this.itemUid = itemUid;
    }

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }
}
