package com.mockuai.marketingcenter.common.domain.dto.mop;

/**
 * Created by zengzhangqiang on 5/31/15.
 */
public class ActivityUidDTO {
    private Long activityId;
    private Long creatorId;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}