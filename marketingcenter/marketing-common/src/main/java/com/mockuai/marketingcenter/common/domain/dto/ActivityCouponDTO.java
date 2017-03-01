package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

public class ActivityCouponDTO extends BaseDTO implements Serializable {

	/**
	 * 单次创建优惠券的数量上限
	 */
	public static final long MAX_COUPON_GEN_COUNT = 9999999L;
	/**
	 * 单次创建优惠券的默认数量
	 */
	public static final long DEFAULT_COUPON_GEN_COUNT = 100000L;

	private Long id;
	private String bizCode;
	private Long activityId;
	private Long activityCreatorId;
	private String name;
	private Integer couponType;
	private String code;
	private Long totalCount;
	private Long grantedCount;
	private Long activateCount;
	/**
	 * 总共使用的次数
	 */
	private Long usedCount;
	/**
	 * 总共领取人数
	 */
	private Long userCount;
	/**
	 * {@link com.mockuai.marketingcenter.common.constant.ActivityCouponStatus}
	 */
	private Integer status;
	/**
	 * 单用户领用数量上限
	 */
	private Integer userReceiveLimit;
	private MarketActivityDTO marketActivity;
	private Integer deleteMark;
	/**
	 * 满 consume 减 discountAmount, 与 持久层 有区别
	 */
	private String content;
	/**
	 * 说明
	 */
	private String desc;
	private Integer lifecycle;
	/**
	 * 使用条件，满 xx 分后能够使用该优惠券
	 */
	private Long consume;
	/**
	 * 优惠的值
	 */
	private Long discountAmount;
	/**
	 * 冗余活动开始时间字段，方便基于活动开始时间做分页查询
	 */
	private Date startTime;
	/**
	 * 冗余活动结束时间字段，方便基于活动结束时间做分页查询
	 */
	private Date endTime;
	/**
	 * 以天为单位
	 */
	private Integer validDuration;
	private Integer open;
	private Date gmtCreated;

	public static long getMaxCouponGenCount() {
		return MAX_COUPON_GEN_COUNT;
	}

	public static long getDefaultCouponGenCount() {
		return DEFAULT_COUPON_GEN_COUNT;
	}

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

	public MarketActivityDTO getMarketActivity() {
		return marketActivity;
	}

	public void setMarketActivity(MarketActivityDTO marketActivity) {
		this.marketActivity = marketActivity;
	}

	public Integer getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getLifecycle() {
		return lifecycle;
	}

	public void setLifecycle(Integer lifecycle) {
		this.lifecycle = lifecycle;
	}

	public Long getConsume() {
		return consume;
	}

	public void setConsume(Long consume) {
		this.consume = consume;
	}

	public Long getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
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

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
}