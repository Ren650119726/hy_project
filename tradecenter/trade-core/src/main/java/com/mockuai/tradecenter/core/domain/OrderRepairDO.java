package com.mockuai.tradecenter.core.domain;

import java.util.Date;

public class OrderRepairDO {
	private Long id;
	private String bizCode;
	
	
	
	private Long orderId;
	
	private Integer repairType;
	private Integer repairStatus;
	
	
	private String brand;
	private String model;
	private String failure;
	private String detailFailure;
	
	
	/**
	 * 逻辑删除标记
	 */
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

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getRepairType() {
		return repairType;
	}

	public void setRepairType(Integer repairType) {
		this.repairType = repairType;
	}

	public Integer getRepairStatus() {
		return repairStatus;
	}

	public void setRepairStatus(Integer repairStatus) {
		this.repairStatus = repairStatus;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getFailure() {
		return failure;
	}

	public void setFailure(String failure) {
		this.failure = failure;
	}

	public String getDetailFailure() {
		return detailFailure;
	}

	public void setDetailFailure(String detailFailure) {
		this.detailFailure = detailFailure;
	}

	
	
	
}