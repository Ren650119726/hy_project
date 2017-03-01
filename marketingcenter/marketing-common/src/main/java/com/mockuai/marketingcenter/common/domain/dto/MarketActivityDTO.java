package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MarketActivityDTO implements Serializable {
	private static final long serialVersionUID = 3357517183458525059L;
	private Long id;
	private String bizCode;
	/**
	 * 活动范围
	 */
	private Integer scope;
	private Integer level;
	private String activityCode;
	/**
	 * 优惠活动名称或标题，例如：注册有礼赠送券、分享有礼赠送券等等
	 */
	private String activityName;
	/**
	 * 优惠活动内容或规则，例如：满100减50
	 */
	private String activityContent;
	private Date startTime;
	private Date endTime;
	/**
	 * 是否用券,  0代表不需要使用优惠券，1代表需要使用优惠券
	 */
	private Integer couponMark;
	/**
	 * {@link com.mockuai.marketingcenter.common.constant.CouponType}
	 */
	private Integer couponType;
	/**
	 * 是否排他, 0代表不排他，1代表排他
	 */
	private Integer exclusiveMark;
	private Long toolId;
	/**
	 * 工具类型：简单工具/复合工具
	 */
	private Integer toolType;
	private String toolCode;
	private Integer creatorType;
	private Long creatorId;
	/**
	 * 商品售空的时间
	 */
	private Date itemInvalidTime;
	private Integer status;
	/**
	 * 适用商品范围 0:仅普通商品 1:所有商品( 1 包含 0, 非普通商品需要传条件 1)
	 */
	private Integer commonItem;
	/**
	 * 优惠活动的生命周期：1代表未开始，2代表进行中，3代表已结束。该属性值直接根据startTime和endTime字段即可推算出来
	 */
	private Integer lifecycle;
	/**
	 * 换购销售数量
	 */
	private Long sales;
	private Long discountAmount;
	/**
	 * 赠品,优惠券
	 */
	private List<ActivityCouponDTO> simpleCouponList;
	/**
	 * 0:不可分佣 1:可分佣
	 */
	private Integer distributable;
	private List<PropertyDTO> propertyList;
	private Map<String, PropertyDTO> propertyMap;
	/**
	 * 条件商品列表
	 */
	private List<ActivityItemDTO> activityItemList;
	/**
	 * 目标商品列表
	 */
	private List<ActivityItemDTO> targetItemList;

	/**
	 * 子活动列表
	 */
	private List<MarketActivityDTO> subMarketActivityList;
	/**
	 * 父活动id
	 */
	private Long parentId;

	/**
	 * 用户领用的优惠券
	 */
	private GrantedCouponDTO grantedCouponDTO;
	/**
	 * 角标
	 */
	private String icon;
	
	private Integer publishStatus;//发布状态
	
	private String activityTag;//活动标签
	
	private String limitTagStatus;//限时活动标签状态(0未开始1已经开始2已结束)
	
	private Long limitTagDate;//限时活动标签时间

	public Long getId() {
		return this.id;
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

	public Integer getScope() {
		return this.scope;
	}

	public void setScope(Integer scope) {
		this.scope = scope;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getActivityCode() {
		return this.activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getActivityName() {
		return this.activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityContent() {
		return activityContent;
	}

	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
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

	public Integer getCouponMark() {
		return this.couponMark;
	}

	public void setCouponMark(Integer couponMark) {
		this.couponMark = couponMark;
	}

	public Integer getCouponType() {
		return this.couponType;
	}

	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}

	public Integer getExclusiveMark() {
		return this.exclusiveMark;
	}

	public void setExclusiveMark(Integer exclusiveMark) {
		this.exclusiveMark = exclusiveMark;
	}

	public Long getToolId() {
		return this.toolId;
	}

	public void setToolId(Long toolId) {
		this.toolId = toolId;
	}

	public Integer getToolType() {
		return toolType;
	}

	public void setToolType(Integer toolType) {
		this.toolType = toolType;
	}

	public String getToolCode() {
		return this.toolCode;
	}

	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}

	public Integer getCreatorType() {
		return this.creatorType;
	}

	public void setCreatorType(Integer creatorType) {
		this.creatorType = creatorType;
	}

	public Long getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Date getItemInvalidTime() {
		return itemInvalidTime;
	}

	public void setItemInvalidTime(Date itemInvalidTime) {
		this.itemInvalidTime = itemInvalidTime;
	}

	public Integer getStatus() {
		return this.status;
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

	public List<PropertyDTO> getPropertyList() {
		return this.propertyList;
	}

	public void setPropertyList(List<PropertyDTO> propertyList) {
		this.propertyList = propertyList;
	}

	public Map<String, PropertyDTO> getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(Map<String, PropertyDTO> propertyMap) {
		this.propertyMap = propertyMap;
	}

	public Long getSales() {
		return sales;
	}

	public void setSales(Long sales) {
		this.sales = sales;
	}

	public List<ActivityCouponDTO> getSimpleCouponList() {
		return simpleCouponList;
	}

	public void setSimpleCouponList(List<ActivityCouponDTO> simpleCouponList) {
		this.simpleCouponList = simpleCouponList;
	}

	public Integer getDistributable() {
		return distributable;
	}

	public void setDistributable(Integer distributable) {
		this.distributable = distributable;
	}

	public Long getDiscountAmount() {
		return this.discountAmount;
	}

	public void setDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
	}

	public List<ActivityItemDTO> getActivityItemList() {
		return activityItemList;
	}

	public void setActivityItemList(List<ActivityItemDTO> activityItemList) {
		this.activityItemList = activityItemList;
	}

	public List<ActivityItemDTO> getTargetItemList() {
		return targetItemList;
	}

	public void setTargetItemList(List<ActivityItemDTO> targetItemList) {
		this.targetItemList = targetItemList;
	}

	public List<MarketActivityDTO> getSubMarketActivityList() {
		return subMarketActivityList;
	}

	public void setSubMarketActivityList(List<MarketActivityDTO> subMarketActivityList) {
		this.subMarketActivityList = subMarketActivityList;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getCommonItem() {
		return commonItem;
	}

	public void setCommonItem(Integer commonItem) {
		this.commonItem = commonItem;
	}

	public GrantedCouponDTO getGrantedCouponDTO() {
		return grantedCouponDTO;
	}

	public void setGrantedCouponDTO(GrantedCouponDTO grantedCouponDTO) {
		this.grantedCouponDTO = grantedCouponDTO;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}

	public String getActivityTag() {
		return activityTag;
	}

	public void setActivityTag(String activityTag) {
		this.activityTag = activityTag;
	}

	public String getLimitTagStatus() {
		return limitTagStatus;
	}

	public void setLimitTagStatus(String limitTagStatus) {
		this.limitTagStatus = limitTagStatus;
	}

	public Long getLimitTagDate() {
		return limitTagDate;
	}

	public void setLimitTagDate(Long limitTagDate) {
		this.limitTagDate = limitTagDate;
	}
}