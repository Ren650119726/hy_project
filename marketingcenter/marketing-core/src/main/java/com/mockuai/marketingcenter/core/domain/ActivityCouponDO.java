package com.mockuai.marketingcenter.core.domain;

import java.util.Date;

public class ActivityCouponDO {

	private Long id;
	private String bizCode;
	/**
	 * 所属优惠活动id
	 */
	private Long activityId;
	/**
	 * 优惠活动创建者id
	 */
	private Long activityCreatorId;
	/**
	 * 优惠券名称,基于名称做搜索
	 */
	private String name;
	/**
	 * 优惠券类型，分为需要激活码的优惠券和不需要激活码的优惠券
	 */
	private Integer couponType;

	private String code;
	/**
	 * 优惠券总数量
	 */
	private Long totalCount;
	/**
	 * 发放/领用数量
	 */
	private Long grantedCount;
	/**
	 * 激活数量（针对需要邀请码来激活的优惠券场景）
	 */
	private Long activateCount;

	// 已使用优惠码的数量，退单不退券
	private Long usedCount;
	/**
	 * 领取的用户数
	 */
	private Long userCount;

	/**
	 * 优惠券状态{@link com.mockuai.marketingcenter.common.constant.ActivityCouponStatus}
	 */
	private Integer status;

	/**
	 * 用户限领张数, 0:不限制 >0: 数量限制 -1: 限领一张，用完再领
	 */
	private Integer userReceiveLimit;
	/**
	 * 冗余活动开始时间字段，方便基于活动开始时间做分页查询
	 */
	private Date startTime;
	/**
	 * 冗余活动结束时间字段，方便基于活动结束时间做分页查询
	 */
	private Date endTime;
	/**
	 * 用户领取后的失效时间,单位为 毫秒
	 */
	private Integer validDuration;
	/**
	 * 是否对外开放领取{@link com.mockuai.marketingcenter.common.constant.CouponOpenEnum}
	 */
	private Integer open;

	private Integer deleteMark;
	private Date gmtCreated;
	private Date gmtModified;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getActivityCreatorId() {
		return activityCreatorId;
	}

	public void setActivityCreatorId(Long activityCreatorId) {
		this.activityCreatorId = activityCreatorId;
	}

	public Integer getCouponType() {
		return couponType;
	}

	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Long getGrantedCount() {
		return grantedCount;
	}

	public void setGrantedCount(Long grantedCount) {
		this.grantedCount = grantedCount;
	}

	public Long getActivateCount() {
		return activateCount;
	}

	public void setActivateCount(Long activateCount) {
		this.activateCount = activateCount;
	}

	public Long getUsedCount() {
		return usedCount;
	}

	public void setUsedCount(Long usedCount) {
		this.usedCount = usedCount;
	}

	public Long getUserCount() {
		return userCount;
	}

	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUserReceiveLimit() {
		return userReceiveLimit;
	}

	public void setUserReceiveLimit(Integer userReceiveLimit) {
		this.userReceiveLimit = userReceiveLimit;
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

	public Integer getValidDuration() {
		return validDuration;
	}

	public void setValidDuration(Integer validDuration) {
		this.validDuration = validDuration;
	}

	public Integer getOpen() {
		return open;
	}

	public void setOpen(Integer open) {
		this.open = open;
	}

	public Integer getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}