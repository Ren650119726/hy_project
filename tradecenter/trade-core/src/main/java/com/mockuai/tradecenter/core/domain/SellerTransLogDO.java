package com.mockuai.tradecenter.core.domain;

import java.util.Date;
/**
 * 收支明细
 * @author hzmk
 *
 */
public class SellerTransLogDO {
	
	private Long id;
	
	private String bizCode;
	
	private String type;
	
	private Long sellerId;
	
	private Long fundInAmount;
	
	private Long fundOutAmount;
	
	private Long lastAmount;
	
	private Long orderId;
	
	private String settlementMark;
	
	private String memo;
	
	private Integer deleteMark;
	
	private Long withdrawId;
	
	private Integer cancelMark;
	
	private Integer paymentId;
	
	private Long userId;
	
	private Date gmtCreated;

    private Date gmtModified;
    
    private String orderSn;
    
    private Long itemSkuId;
    
    private Integer shopType;
    
//    private Integer mallMark;


	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLastAmount() {
		return lastAmount;
	}

	public void setLastAmount(Long lastAmount) {
		this.lastAmount = lastAmount;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getFundInAmount() {
		return fundInAmount;
	}

	public void setFundInAmount(Long fundInAmount) {
		this.fundInAmount = fundInAmount;
	}

	public Long getFundOutAmount() {
		return fundOutAmount;
	}

	public void setFundOutAmount(Long fundOutAmount) {
		this.fundOutAmount = fundOutAmount;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getSettlementMark() {
		return settlementMark;
	}

	public void setSettlementMark(String settlementMark) {
		this.settlementMark = settlementMark;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}

	public Long getWithdrawId() {
		return withdrawId;
	}

	public void setWithdrawId(Long withdrawId) {
		this.withdrawId = withdrawId;
	}

	public Integer getCancelMark() {
		return cancelMark;
	}

	public void setCancelMark(Integer cancelMark) {
		this.cancelMark = cancelMark;
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

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public Long getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}

	public Integer getShopType() {
		return shopType;
	}

	public void setShopType(Integer shopType) {
		this.shopType = shopType;
	}

//	public Integer getMallMark() {
//		return mallMark;
//	}
//
//	public void setMallMark(Integer mallMark) {
//		this.mallMark = mallMark;
//	}

	
	


}
