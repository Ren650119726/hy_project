package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单使用优惠券传输对象
 */
public class UsedCouponDTO implements Serializable {
    private Long couponId;
    private Long userId;
    private String name;
    private String toolCode;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }
}