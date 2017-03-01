package com.mockuai.tradecenter.common.domain.refund;

import java.util.Date;
import java.util.List;

import com.mockuai.tradecenter.common.domain.BaseDTO;

public class RefundItemLogDTO extends BaseDTO {

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
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

	public Long getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getRefundReasonId() {
		return refundReasonId;
	}

	public void setRefundReasonId(Integer refundReasonId) {
		this.refundReasonId = refundReasonId;
	}

	public Integer getOperatorFrom() {
		return operatorFrom;
	}

	public void setOperatorFrom(Integer operatorFrom) {
		this.operatorFrom = operatorFrom;
	}

	public List<RefundImageDTO> getRefundImageList() {
		return refundImageList;
	}

	public void setRefundImageList(List<RefundImageDTO> refundImageList) {
		this.refundImageList = refundImageList;
	}
	
	

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}
	
	



	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}





	public String getRefundDesc() {
		return refundDesc;
	}

	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
	}





	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}





	private Long orderId;

	private Long id;

	private Long itemId;// 商品ID

	private Long sellerId;// 供应商ID
	
	private String userName;// 操作人姓名

	private Long refundAmount;

	private Integer refundReasonId;
	
	private String refundReason;

	private String refuseReason;

	private Integer refundStatus;

	private String bizCode;

	private Long itemSkuId;

	private Integer deleteMark;

	private Date gmtCreated;

	private Date gmtModified;

	private Integer operatorFrom;

	private List<RefundImageDTO> refundImageList;

	private Integer refundType;
	
	private String refundDesc;
}
