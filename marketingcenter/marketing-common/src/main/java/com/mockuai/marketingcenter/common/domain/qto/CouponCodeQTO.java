package com.mockuai.marketingcenter.common.domain.qto;

import com.mockuai.marketingcenter.common.constant.UserCouponStatus;

import java.io.Serializable;
import java.util.List;

/**
 * 优惠码值查询
 */
public class CouponCodeQTO extends PageQTO implements Serializable {

    private Long couponId;
    private Long activityCreatorId;
    /**
     * {@link com.mockuai.marketingcenter.common.constant.UserCouponStatus}
     */
    private Integer status;

    /**
     * 查询中，已使用的包括了两种状态，PRE_USE, USE
     */
    private Integer compareStatus = UserCouponStatus.USED.getValue();
    /**
     * 优惠码搜索
     */
    private String code;
    /**
     * 用户名查询
     */
    private String userName;

    private List<Long> userIdList;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
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

    public Integer getCompareStatus() {
        return compareStatus;
    }

    public void setCompareStatus(Integer compareStatus) {
        this.compareStatus = compareStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Long> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Long> userIdList) {
        this.userIdList = userIdList;
    }
}