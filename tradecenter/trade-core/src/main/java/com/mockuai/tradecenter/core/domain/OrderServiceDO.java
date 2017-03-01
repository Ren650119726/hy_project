package com.mockuai.tradecenter.core.domain;

import java.util.Date;

public class OrderServiceDO {
	private Long id;
	private String bizCode;
	
	
	
	private Long orderId;
	private Long sellerId;
	private Long itemSkuId;
	private Long orderItemId;
	private String serviceName;
	private Long price;
	private String serviceImageUrl;
	
	
	private Long serviceId;
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
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public Long getItemSkuId() {
		return itemSkuId;
	}
	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getServiceImageUrl() {
		return serviceImageUrl;
	}
	public void setServiceImageUrl(String serviceImageUrl) {
		this.serviceImageUrl = serviceImageUrl;
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
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public Long getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	
	
}