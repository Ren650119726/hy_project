package com.mockuai.marketingcenter.common.domain.qto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MarketActivityQTO extends PageQTO implements Serializable {
	private static final long serialVersionUID = 1;
	private Long id;
	private String bizCode;
	private List<Long> idList;
	private Integer scope;
	private List<Integer> scopeList;
	private Integer level;
	private Long toolId;
	private Long creatorId;
	private Integer status;
	private Integer toolType;
	private Long parentId;
	private List<Long> parentIdList;
	/**
	 * 优惠活动生命周期状态，0代表查询全部，1代表未开始，2代表进行中，3代表已结束
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
	/**
	 * 0:仅普通商品 1:所有商品
	 */
	private Integer commonItem;
	/**
	 * 活动名称
	 */
	private String activityName;

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	public Integer getScope() {
		return scope;
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

	public Long getToolId() {
		return toolId;
	}

	public void setToolId(Long toolId) {
		this.toolId = toolId;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getToolType() {
		return toolType;
	}

	public void setToolType(Integer toolType) {
		this.toolType = toolType;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<Long> getParentIdList() {
		return parentIdList;
	}

	public void setParentIdList(List<Long> parentIdList) {
		this.parentIdList = parentIdList;
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

	public List<Integer> getScopeList() {
		return scopeList;
	}

	public void setScopeList(List<Integer> scopeList) {
		this.scopeList = scopeList;
	}

	public Integer getCommonItem() {
		return commonItem;
	}

	public void setCommonItem(Integer commonItem) {
		this.commonItem = commonItem;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
}