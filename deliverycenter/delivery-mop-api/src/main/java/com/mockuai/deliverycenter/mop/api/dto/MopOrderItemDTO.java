package com.mockuai.deliverycenter.mop.api.dto;

import java.io.Serializable;
import java.util.List;


public class MopOrderItemDTO implements Serializable {
	private Long id;
	private String bizCode;
	/**
	 * 下单用户ID
	 */
	private Long userId;
	/**
	 * 下单用户昵称
	 */
	private String userName;
	/**
	 * 所属订单ID
	 */
	private Long orderId;
	/**
	 * 商品ID
	 */
	private Long itemId;
	/**
	 * 商品名称
	 */
	private String itemName;
	/**
	 * 商品主图URL
	 */
	private String itemImageUrl;
	/**
	 * 商品SKU ID
	 */
	private Long itemSkuId;
	/**
	 * 商品规格描述
	 */
	private String itemSkuDesc;
	/**
	 * 商品卖家ID
	 */
	private Long sellerId;
	/**
	 * 商品单价
	 */
	private Long unitPrice;

	private String unitPriceStr;
	/**
	 * 商品的发货方式
	 */
	private Integer deliveryType;
	/**
	 * 下单数量
	 */
	private Integer number;

	private String sellerName;

	private Long categoryId;

	private Long itemBrandId;
	
	private String itemUrl;
	
	private Integer itemType;
	
	private String outTradeNo; //第三方流水
	
	private Long refundAmount;//退款金额
	
	private Long paymentAmount;//实付金额
	
	private Integer refundStatus;
	
    private Integer refundReasonId;
    
    private Integer refundType;
    
    private Long discountAmount;
    
    private Long point;
    
    private Integer deliveryMark;

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemImageUrl() {
		return itemImageUrl;
	}

	public void setItemImageUrl(String itemImageUrl) {
		this.itemImageUrl = itemImageUrl;
	}

	public Long getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}

	public String getItemSkuDesc() {
		return itemSkuDesc;
	}

	public void setItemSkuDesc(String itemSkuDesc) {
		this.itemSkuDesc = itemSkuDesc;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Long unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}


	public String getUnitPriceStr() {
		return unitPriceStr;
	}

	public void setUnitPriceStr(String unitPriceStr) {
		this.unitPriceStr = unitPriceStr;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getItemBrandId() {
		return itemBrandId;
	}

	public void setItemBrandId(Long itemBrandId) {
		this.itemBrandId = itemBrandId;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Long getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Integer getRefundReasonId() {
		return refundReasonId;
	}

	public void setRefundReasonId(Integer refundReasonId) {
		this.refundReasonId = refundReasonId;
	}

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
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
	
	

}
