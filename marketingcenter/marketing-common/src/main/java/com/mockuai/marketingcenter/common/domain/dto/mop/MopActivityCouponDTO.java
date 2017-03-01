package com.mockuai.marketingcenter.common.domain.dto.mop;

import java.io.Serializable;

/**
 * Created by edgar.zr on 8/19/15.
 */
public class MopActivityCouponDTO implements Serializable {

	private String activityCouponUid;
	private String activityUid;
	private Long totalCount;
	private Integer lifecycle;
	private String startTime;
	private String endTime;
	private String name;
	/**
	 * 满 consume 减 discountAmount
	 */
	private String content;
	/**
	 * 优惠券说明
	 */
	private String desc;
	private Integer status;
	private Long consume;
	private Long discountAmount;

	public String getActivityCouponUid() {
		return activityCouponUid;
	}

	public void setActivityCouponUid(String activityCouponUid) {
		this.activityCouponUid = activityCouponUid;
	}

	public String getActivityUid() {
		return activityUid;
	}

	public void setActivityUid(String activityUid) {
		this.activityUid = activityUid;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getLifecycle() {
		return lifecycle;
	}

	public void setLifecycle(Integer lifecycle) {
		this.lifecycle = lifecycle;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
}