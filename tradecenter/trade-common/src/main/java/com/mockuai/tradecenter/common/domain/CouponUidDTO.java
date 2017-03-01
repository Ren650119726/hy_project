package com.mockuai.tradecenter.common.domain;

/**
 * Created by zengzhangqiang on 5/29/15.
 */
public class CouponUidDTO {
    private Long couponId;
    private Long userId;

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
}
