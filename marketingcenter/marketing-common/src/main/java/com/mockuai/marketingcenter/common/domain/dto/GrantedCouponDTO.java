package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GrantedCouponDTO implements Serializable {
	private Long id;
	private List<Long> idList;
	private Long couponId;
	private Long couponCreatorId;
	private Integer couponType;
	private String code;
	private Long activityId;
	private Long activityCreatorId;
	private Date startTime;
	private Date endTime;
	private Date useTime;
	private Date invalidTime;
	private Long orderId;
	/**
	 * 订单编号
	 */
	private String orderSn;
	private Long granterId;
	private Long receiverId;
	// 领取人名字
	private String receiverName;
	private Date activateTime;

	// 发放人
	private String granterName;
	private Integer status;
	private Long consume;
	private Long discountAmount;
	// 优惠券使用范围
	private Integer scope;
	private List<PropertyDTO> propertyList;
	private String toolCode;
	private String bizCode;
	private String name;
	private String content;
	private String desc;

	/**
	 * 0:无 1:接近过期时间
	 */
	private Integer nearExpireDate;
	/**
	 * 发放理由
	 */
	private String text;
	/**
	 * 发放途径
	 */
	private Integer grantSource;

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

	public Long getCouponId() {
		return this.couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Long getCouponCreatorId() {
		return this.couponCreatorId;
	}

	public void setCouponCreatorId(Long couponCreatorId) {
		this.couponCreatorId = couponCreatorId;
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

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public Long getConsume() {
		return consume;
	}

	public void setConsume(Long consume) {
		this.consume = consume;
	}

	public Long getActivityId() {
		return this.activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Long getActivityCreatorId() {
		return this.activityCreatorId;
	}

	public void setActivityCreatorId(Long activityCreatorId) {
		this.activityCreatorId = activityCreatorId;
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

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
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

	public Long getDiscountAmount() {
		return this.discountAmount;
	}

	public void setDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Integer getScope() {
		return scope;
	}

	public void setScope(Integer scope) {
		this.scope = scope;
	}

	public List<PropertyDTO> getPropertyList() {
		return this.propertyList;
	}

	public void setPropertyList(List<PropertyDTO> propertyList) {
		this.propertyList = propertyList;
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

	public Integer getNearExpireDate() {
		return nearExpireDate;
	}

	public void setNearExpireDate(Integer nearExpireDate) {
		this.nearExpireDate = nearExpireDate;
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

	public Date getActivateTime() {
		return activateTime;
	}

	public void setActivateTime(Date activateTime) {
		this.activateTime = activateTime;
	}

	public String getGranterName() {
		return granterName;
	}

	public void setGranterName(String granterName) {
		this.granterName = granterName;
	}
}