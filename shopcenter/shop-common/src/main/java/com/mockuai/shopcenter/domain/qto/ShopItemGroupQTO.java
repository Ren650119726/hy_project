package com.mockuai.shopcenter.domain.qto;

import com.mockuai.shopcenter.page.PageInfo;

import java.io.Serializable;

/**
 * Created by luliang on 15/7/26.
 */
public class ShopItemGroupQTO extends PageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String bizCode;
    private Long sellerId;
    private Long shopId;
    private String groupName;
    private Integer deleteMark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
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

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

}
