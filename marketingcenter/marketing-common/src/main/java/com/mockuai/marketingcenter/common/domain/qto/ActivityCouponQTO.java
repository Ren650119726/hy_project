package com.mockuai.marketingcenter.common.domain.qto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ActivityCouponQTO extends PageQTO implements Serializable {

    private Long id;
    private List<Long> idList;
    private String bizCode;
    private Integer level;
    private String code;
    /**
     * 优惠券名称
     */
    private String name;
    /**
     * 是否为有码券
     */
    private Boolean hasCode;
    private Long activityId;
    private List<Long> activityIdList;
    private Long activityCreatorId;
    /**
     * 优惠券状态
     */
    private Integer status;
    /**
     * 优惠券生命周期状态，0代表查询全部，1代表未开始，2代表进行中，3代表已结束
     */
    private Integer lifecycle;
    /**
     * 小于开始时间
     */
    private Date startTimeLt;
    /**
     * 大于等于开始时间
     */
    private Date startTimeGe;
    /**
     * 小于等于结束时间
     */
    private Date endTimeLe;
    /**
     * 大于结束时间
     */
    private Date endTimeGt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHasCode() {
        return hasCode;
    }

    public void setHasCode(Boolean hasCode) {
        this.hasCode = hasCode;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public List<Long> getActivityIdList() {
        return activityIdList;
    }

    public void setActivityIdList(List<Long> activityIdList) {
        this.activityIdList = activityIdList;
    }

    public Long getActivityCreatorId() {
        return activityCreatorId;
    }

    public void setActivityCreatorId(Long activityCreatorId) {
        this.activityCreatorId = activityCreatorId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(Integer lifecycle) {
        this.lifecycle = lifecycle;
    }

    public Date getStartTimeLt() {
        return startTimeLt;
    }

    public void setStartTimeLt(Date startTimeLt) {
        this.startTimeLt = startTimeLt;
    }

    public Date getStartTimeGe() {
        return startTimeGe;
    }

    public void setStartTimeGe(Date startTimeGe) {
        this.startTimeGe = startTimeGe;
    }

    public Date getEndTimeLe() {
        return endTimeLe;
    }

    public void setEndTimeLe(Date endTimeLe) {
        this.endTimeLe = endTimeLe;
    }

    public Date getEndTimeGt() {
        return endTimeGt;
    }

    public void setEndTimeGt(Date endTimeGt) {
        this.endTimeGt = endTimeGt;
    }
}
