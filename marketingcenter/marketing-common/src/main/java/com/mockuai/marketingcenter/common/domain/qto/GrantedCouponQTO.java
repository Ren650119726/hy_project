package com.mockuai.marketingcenter.common.domain.qto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GrantedCouponQTO extends PageQTO implements Serializable {
	private Long id;
	private List<Long> idList;
	private Long granterId;
	private Long receiverId;
	private List<Long> receiverIdList;
	private Integer status;
	private Integer compareStatus = 50;
	private List<Integer> statusList;
	/**
	 * 是否过期, 0 : 未过期 1 : 已过期
	 */
	private Integer outOfDate;
	private String couponCode;
	private Long couponId;
	private Long activityId;
	private List<Long> activityIds;
	private Long orderId;
	/**
	 * 优惠码值
	 */
	private List<String> codeList;
	private String bizCode;
	private Integer grantSource;
	private Date startTime;
	private Date endTime;
	private String userName;
	private String operatorName;

	public Long getId() {
		return this.id;
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

	public List<Long> getReceiverIdList() {
		return receiverIdList;
	}

	public void setReceiverIdList(List<Long> receiverIdList) {
		this.receiverIdList = receiverIdList;
	}

	public Integer getStatus() {
		return this.status;
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

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

	public String getCouponCode() {
		return this.couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public Integer getOutOfDate() {
		return outOfDate;
	}

	public void setOutOfDate(Integer outOfDate) {
		this.outOfDate = outOfDate;
	}

	public Long getCouponId() {
		return this.couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Long getActivityId() {
		return this.activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<String> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<String> codeList) {
		this.codeList = codeList;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public List<Long> getActivityIds() {
		return activityIds;
	}

	public void setActivityIds(List<Long> activityIds) {
		this.activityIds = activityIds;
	}

	public Integer getGrantSource() {
		return grantSource;
	}

	public void setGrantSource(Integer grantSource) {
		this.grantSource = grantSource;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
}