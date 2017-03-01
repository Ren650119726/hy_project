package com.mockuai.tradecenter.common.domain.refund;

import java.util.Date;
import java.util.List;

import com.mockuai.tradecenter.common.domain.BaseDTO;
import com.mockuai.tradecenter.common.domain.OrderServiceDTO;

public class RefundOrderItemDTO extends BaseDTO{
	private String orderSn;

	private Long userId;

	private Integer auditResult;

	private Integer refundStatus;

	private Long orderId;

	private Long itemSkuId ;

	private Long sellerId;

	private Integer number;

	private Integer refundReasonId;

	private String  refundReason;

	private String refundDesc;

	private String returnAttach;

	private Long refundAmount;

	private Long paymentAmount;

	private Long discountAmount;

	private Long point;

	private Long pointAmount;

	private Long itemId;

	private List<RefundImageDTO> refundImageList;

	private List<RefundItemLogDTO> refundItemLogList;


	private String bizCode;

	private String refuseReason;

	private Integer refundType;

	private String itemName;

	private String itemSkuDesc;

	private String sellerName;

	/**
	 * 商品主图URL
	 */
	private String itemImageUrl;

	private String refundBatchNo;

	private Integer deliveryMark;

	private Date refundTime;

	private List<OrderServiceDTO> orderServiceList;

	private Long id;

	private Long orderItemId;
	
	public Long getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}


	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}


	public String getReturnAttach() {
		return returnAttach;
	}

	public void setReturnAttach(String returnAttach) {
		this.returnAttach = returnAttach;
	}
	

	
	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getRefundReasonId() {
		return refundReasonId;
	}

	public void setRefundReasonId(Integer refundReasonId) {
		this.refundReasonId = refundReasonId;
	}

	public String getRefundDesc() {
		return refundDesc;
	}

	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
	}

	public List<RefundImageDTO> getRefundImageList() {
		return refundImageList;
	}

	public void setRefundImageList(List<RefundImageDTO> refundImageList) {
		this.refundImageList = refundImageList;
	}
	
	

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}



	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	
	
	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	
	
	public Integer getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(Integer auditResult) {
		this.auditResult = auditResult;
	}
	
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
	
	

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}



	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}



	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	


	public List<RefundItemLogDTO> getRefundItemLogList() {
		return refundItemLogList;
	}

	public void setRefundItemLogList(List<RefundItemLogDTO> refundItemLogList) {
		this.refundItemLogList = refundItemLogList;
	}

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemSkuDesc() {
		return itemSkuDesc;
	}

	public void setItemSkuDesc(String itemSkuDesc) {
		this.itemSkuDesc = itemSkuDesc;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getItemImageUrl() {
		return itemImageUrl;
	}

	public void setItemImageUrl(String itemImageUrl) {
		this.itemImageUrl = itemImageUrl;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public String getRefundBatchNo() {
		return refundBatchNo;
	}

	public void setRefundBatchNo(String refundBatchNo) {
		this.refundBatchNo = refundBatchNo;
	}

	public Long getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Long getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public Integer getDeliveryMark() {
		return deliveryMark;
	}

	public void setDeliveryMark(Integer deliveryMark) {
		this.deliveryMark = deliveryMark;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public Long getPointAmount() {
		return pointAmount;
	}

	public void setPointAmount(Long pointAmount) {
		this.pointAmount = pointAmount;
	}

	public List<OrderServiceDTO> getOrderServiceList() {
		return orderServiceList;
	}

	public void setOrderServiceList(List<OrderServiceDTO> orderServiceList) {
		this.orderServiceList = orderServiceList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}
}
