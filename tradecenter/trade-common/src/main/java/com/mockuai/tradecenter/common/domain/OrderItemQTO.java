package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.List;

public class OrderItemQTO extends BaseQTO implements Serializable {

	private Long orderItemId;
	private Long orderId;
	private Long userId;
	private List<Long> orderIdList;
	private String itemName;
	private Long itemId;
	private Long itemSkuId;
	private String bizCode;
	private String outTradeNo;
	private Long paymentAmount;
	private Long shareUserId;

	public Long getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(Long shareUserId) {
		this.shareUserId = shareUserId;
	}

	/**
	 * 删除标记
	 */
	private Integer deleteMark;
	
	private boolean  noIncludeRefund;
	
	private String refundStatus;
	
	private String refundBatchNo;
	private List<Long> skuIdList;
	
	private Integer deliveryMark;
	
	private Long deliveryInfoId;
	
	/*退款申请超时自动拒绝天数*/
	private int timeoutAutoRefundDay;
	
	private Long originalSkuId;
	
	private List<Long> itemIds;

	public Integer getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Long getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Long> getOrderIdList() {
		return orderIdList;
	}

	public void setOrderIdList(List<Long> orderIdList) {
		this.orderIdList = orderIdList;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public boolean isNoIncludeRefund() {
		return noIncludeRefund;
	}

	public void setNoIncludeRefund(boolean noIncludeRefund) {
		this.noIncludeRefund = noIncludeRefund;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public Long getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getRefundBatchNo() {
		return refundBatchNo;
	}

	public void setRefundBatchNo(String refundBatchNo) {
		this.refundBatchNo = refundBatchNo;
	}

	public List<Long> getSkuIdList() {
		return skuIdList;
	}

	public void setSkuIdList(List<Long> skuIdList) {
		this.skuIdList = skuIdList;
	}

	public Integer getDeliveryMark() {
		return deliveryMark;
	}

	public void setDeliveryMark(Integer deliveryMark) {
		this.deliveryMark = deliveryMark;
	}

	public Long getDeliveryInfoId() {
		return deliveryInfoId;
	}

	public void setDeliveryInfoId(Long deliveryInfoId) {
		this.deliveryInfoId = deliveryInfoId;
	}

	public int getTimeoutAutoRefundDay() {
		return timeoutAutoRefundDay;
	}

	public void setTimeoutAutoRefundDay(int timeoutAutoRefundDay) {
		this.timeoutAutoRefundDay = timeoutAutoRefundDay;
	}

	public Long getOriginalSkuId() {
		return originalSkuId;
	}

	public void setOriginalSkuId(Long originalSkuId) {
		this.originalSkuId = originalSkuId;
	}

	public List<Long> getItemIds() {
		return itemIds;
	}

	public void setItemIds(List<Long> itemIds) {
		this.itemIds = itemIds;
	}

	
	
}
