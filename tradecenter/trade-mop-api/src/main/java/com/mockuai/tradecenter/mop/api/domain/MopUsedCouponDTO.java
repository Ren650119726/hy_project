package com.mockuai.tradecenter.mop.api.domain;

/**
 * Created by zengzhangqiang on 5/29/15.
 */
public class MopUsedCouponDTO {
    private String couponUid;
    private String name;
    private String toolCode;

    public String getCouponUid() {
        return couponUid;
    }

    public void setCouponUid(String couponUid) {
        this.couponUid = couponUid;
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
