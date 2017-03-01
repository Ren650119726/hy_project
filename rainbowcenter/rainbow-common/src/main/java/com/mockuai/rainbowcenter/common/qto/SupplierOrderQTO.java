package com.mockuai.rainbowcenter.common.qto;

import java.util.Date;

public class SupplierOrderQTO extends QueryPage{
	private static final long serialVersionUID = -4185799452061365187L;

	private Long id;
	
	private String bizCode;
	
	private Long orderId;
	
	private Long sellerId;
	
	private String orderSn;
	/** 
	 * 订单状态
	 */
	private Integer orderStatus;
	
	private String declarationNo; 
	
	private Long supplierId;
	
	private Byte syncStatus;
	
	private Long deleteVersion;
	
	private Byte deleteMark;
	
	private Date gmtCreated;
	
	private Date gmtModified;
	
	/**
	 * 订单开始时间
	 */
	private Date orderTimeStart;

	/**
	 * 订单结束时间
	 */
	private Date orderTimeEnd;

	public Date getOrderTimeStart() {
		return orderTimeStart;
	}

	public void setOrderTimeStart(Date orderTimeStart) {
		this.orderTimeStart = orderTimeStart;
	}

	public Date getOrderTimeEnd() {
		return orderTimeEnd;
	}

	public void setOrderTimeEnd(Date orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
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

	public Long getOrderId() {
		return orderId;
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

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}


	public Long getDeleteVersion() {
		return deleteVersion;
	}

	public void setDeleteVersion(Long deleteVersion) {
		this.deleteVersion = deleteVersion;
	}

	
	public Byte getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Byte syncStatus) {
		this.syncStatus = syncStatus;
	}

	public Byte getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(Byte deleteMark) {
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

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getDeclarationNo() {
		return declarationNo;
	}

	public void setDeclarationNo(String declarationNo) {
		this.declarationNo = declarationNo;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	
   
}
