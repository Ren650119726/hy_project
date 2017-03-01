package com.mockuai.marketingcenter.common.domain.qto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by huangsiqian on 2016/11/3.
 */
public class ViewSalesQTO extends PageQTO implements Serializable {
    private Long activityId;
    private String goodsName;
    private String goodsId;
    private String orderSn;
    private Date payTime;
    private Long limitedPrice;
    private Integer goodsNum;
    private String userPhone;
    private String sharePhone;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }



    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Long getLimitedPrice() {
        return limitedPrice;
    }

    public void setLimitedPrice(Long limitedPrice) {
        this.limitedPrice = limitedPrice;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getSharePhone() {
        return sharePhone;
    }

    public void setSharePhone(String sharePhone) {
        this.sharePhone = sharePhone;
    }
}
