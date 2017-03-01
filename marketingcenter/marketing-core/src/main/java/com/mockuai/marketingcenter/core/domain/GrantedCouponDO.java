package com.mockuai.marketingcenter.core.domain;

import java.util.Date;

public class GrantedCouponDO {
	private Long id;
	private String bizCode;
	private Long couponId;
	private Long couponCreatorId;
	private Integer couponType;
	// 优惠码字段
	private String code;
	private Long activityId;
	private Long activityCreatorId;
	private Date startTime;
	private Date endTime;
	// 优惠券失效时间点
	private Date invalidTime;
	// 领取时间
	private Date activateTime;
	// 使用的时间
	private Date useTime;
	private Long orderId;
	private Long granterId;
	private Long receiverId;
	private String toolCode;
	/**
	 * 发放理由
	 */
	private String text;
	/**
	 * 发放途径{@link com.mockuai.marketingcenter.common.constant.GrantSourceEnum}
	 */
	private Integer grantSource;
	private Integer status;
	private Integer deleteMark;
	private Date gmtCreated;
	private Date gmtModified;

	public Long getId() {

		return this.id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public Long getCouponCreatorId() {

		return this.couponCreatorId;
	}

	public void setCouponCreatorId(Long couponCreatorId) {

		this.couponCreatorId = couponCreatorId;
	}

	public Long getActivityCreatorId() {

		return this.activityCreatorId;
	}

	public void setActivityCreatorId(Long activityCreatorId) {

		this.activityCreatorId = activityCreatorId;
	}

	public Long getCouponId() {

		return this.couponId;
	}

	public void setCouponId(Long couponId) {

		this.couponId = couponId;
	}

	public Integer getCouponType() {

		return this.couponType;
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

	public Long getActivityId() {

		return this.activityId;
	}

	public void setActivityId(Long activityId) {

		this.activityId = activityId;
	}

	public Date getStartTime() {

		return this.startTime;
	}

	public void setStartTime(Date startTime) {

		this.startTime = startTime;
	}

	public Date getEndTime() {

		return this.endTime;
	}

	public void setEndTime(Date endTime) {

		this.endTime = endTime;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public Date getActivateTime() {
		return activateTime;
	}

	public void setActivateTime(Date activateTime) {
		this.activateTime = activateTime;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public Long getOrderId() {

		return this.orderId;
	}

	public void setOrderId(Long orderId) {

		this.orderId = orderId;
	}

	public Long getGranterId() {

		return this.granterId;
	}

	public void setGranterId(Long granterId) {

		this.granterId = granterId;
	}

	public Long getReceiverId() {

		return this.receiverId;
	}

	public void setReceiverId(Long receiverId) {

		this.receiverId = receiverId;
	}

	public Integer getStatus() {

		return this.status;
	}

	public void setStatus(Integer status) {

		this.status = status;
	}

	public String getToolCode() {

		return this.toolCode;
	}

	public void setToolCode(String toolCode) {

		this.toolCode = toolCode;
	}

	public String getBizCode() {

		return this.bizCode;
	}

	public void setBizCode(String bizCode) {

		this.bizCode = bizCode;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getGrantSource() {
		return grantSource;
	}

	public void setGrantSource(Integer grantSource) {
		this.grantSource = grantSource;
	}

	public Integer getDeleteMark() {

		return this.deleteMark;
	}

	public void setDeleteMark(Integer deleteMark) {

		this.deleteMark = deleteMark;
	}

	public Date getGmtCreated() {

		return this.gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {

		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {

		return this.gmtModified;
	}

	public void setGmtModified(Date gmtModified) {

		this.gmtModified = gmtModified;
	}
}