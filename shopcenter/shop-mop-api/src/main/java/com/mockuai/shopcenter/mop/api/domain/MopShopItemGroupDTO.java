package com.mockuai.shopcenter.mop.api.domain;

import java.io.Serializable;

/**
 * Created by luliang on 15/8/6.
 */
public class MopShopItemGroupDTO implements Serializable {

    private String itemGroupUid;
    private Long sellerId;
    private Long shopId;
    private String groupName;
    private String createTime;

    public String getItemGroupUid() {
        return itemGroupUid;
    }

    public void setItemGroupUid(String itemGroupUid) {
        this.itemGroupUid = itemGroupUid;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
