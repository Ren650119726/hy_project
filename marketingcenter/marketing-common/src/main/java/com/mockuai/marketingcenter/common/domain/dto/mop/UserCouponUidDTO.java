package com.mockuai.marketingcenter.common.domain.dto.mop;

/**
 * Created by zengzhangqiang on 5/31/15.
 */
public class UserCouponUidDTO {
    private Long userCouponId;
    private Long userId;

    public Long getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(Long userCouponId) {
        this.userCouponId = userCouponId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}