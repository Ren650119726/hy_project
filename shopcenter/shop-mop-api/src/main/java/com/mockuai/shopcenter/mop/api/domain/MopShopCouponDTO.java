package com.mockuai.shopcenter.mop.api.domain;

import java.util.Date;

/**
 * Created by yindingyu on 16/1/16.
 */
public class MopShopCouponDTO {

    private long shopId;

    private String activityCouponUid;

    private String name;

    private long totalCount;

    private int status;

    private int lifecycle;

    private String content;

    private Date startTime;

    private Date endTime;

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getActivityCouponUid() {
        return activityCouponUid;
    }

    public void setActivityCouponUid(String activityCouponUid) {
        this.activityCouponUid = activityCouponUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(int lifecycle) {
        this.lifecycle = lifecycle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getConsume() {
        return consume;
    }

    public void setConsume(long consume) {
        this.consume = consume;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(long discountAmount) {
        this.discountAmount = discountAmount;
    }

    private long consume;

    private long discountAmount;


}
