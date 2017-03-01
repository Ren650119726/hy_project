package com.mockuai.shopcenter.domain.qto;

import com.mockuai.shopcenter.page.PageInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by luliang on 15/8/7.
 */
public class ShopCollectionQTO extends PageInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id; // 主键

    private Long userId; //用户ID

    private Long sellerId;//商家ID

    private Long shopId;//店铺ID

    private String bizCode;

    private Integer deleteMark;

    private Date gmtCreated;//收藏时间

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

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }
}
