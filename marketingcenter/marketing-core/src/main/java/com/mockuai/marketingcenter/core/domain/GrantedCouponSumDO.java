package com.mockuai.marketingcenter.core.domain;

/**
 * Created by zengzhangqiang on 8/7/15.
 */
public class GrantedCouponSumDO {

    private Long activityId;
    private Long activityCreatorId;
    private Integer count;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getActivityCreatorId() {
        return activityCreatorId;
    }

    public void setActivityCreatorId(Long activityCreatorId) {
        this.activityCreatorId = activityCreatorId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
