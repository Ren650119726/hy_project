package com.mockuai.shopcenter.domain.dto;

import java.io.Serializable;

/**
 * 店铺收藏;
 * Created by luliang on 15/8/7.
 */
public class ShopCollectionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id; // 主键

    private Long userId; //用户ID

    private Long sellerId;//商家ID

    private Long shopId;//店铺ID

    private String bizCode;

    private Integer deleteMark; //是否删除

    private String createTime;//收藏时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
