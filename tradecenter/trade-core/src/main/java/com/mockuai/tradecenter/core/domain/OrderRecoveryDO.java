package com.mockuai.tradecenter.core.domain;

import java.util.Date;

public class OrderRecoveryDO {
	private Long id;
	private String bizCode;
	
	
	
	private Long orderId;
	
	private String recoveryType;
	private String recoveryStatus;
	
	
	private String brand;
	private String model;
	private String specification; //规格
	private String damage;//破损程度
	
	
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

	public String getRecoveryType() {
		return recoveryType;
	}

	public void setRecoveryType(String recoveryType) {
		this.recoveryType = recoveryType;
	}

	public String getRecoveryStatus() {
		return recoveryStatus;
	}

	public void setRecoveryStatus(String recoveryStatus) {
		this.recoveryStatus = recoveryStatus;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getDamage() {
		return damage;
	}

	public void setDamage(String damage) {
		this.damage = damage;
	}


	
	
	
}