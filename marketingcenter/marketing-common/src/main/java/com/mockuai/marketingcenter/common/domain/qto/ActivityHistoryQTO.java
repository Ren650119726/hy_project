package com.mockuai.marketingcenter.common.domain.qto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by edgar.zr on 12/3/15.
 */
public class ActivityHistoryQTO extends PageQTO implements Serializable {
    private Long id;
    private String bizCode;
    private Long activityId;
    private Long skuId;
    private Long userId;
    private Integer status;
    private List<Integer> statusList;
    private List<Long> activityList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public List<Long> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Long> activityList) {
        this.activityList = activityList;
    }
}