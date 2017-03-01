package com.mockuai.tradecenter.common.domain.settlement;

import java.util.Date;

import com.mockuai.tradecenter.common.domain.BaseDTO;

public class SellerTransLogDTO extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 31969310287534761L;

	private String bizCode;

	private String type;

	private Long sellerId;

	private Long fundInAmount;

	private Long fundOutAmount;
	
	private Long lastAmount;
	
	private Long freezeAmount;
	
	private Long orderId;
	
	private Long withdrawId;
	
	private String settlementMark;//结算标志
	

	private Integer paymentId;
	
	private Long userId;
	
	private String orderSn;
	
	
	private Date gmtCreated;

    private Date gmtModified;
	
    private Long itemSkuId;
    
    private Integer shopType;
    
    private String memo;
    
    private String shopName;
	
	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
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

	public Long getWithdrawId() {
		return withdrawId;
	}

	public void setWithdrawId(Long withdrawId) {
		this.withdrawId = withdrawId;
	}

	public String getSettlementMark() {
		return settlementMark;
	}

	public void setSettlementMark(String settlementMark) {
		this.settlementMark = settlementMark;
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

	public Long getLastAmount() {
		return lastAmount;
	}

	public void setLastAmount(Long lastAmount) {
		this.lastAmount = lastAmount;
	}

	public Long getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(Long freezeAmount) {
		this.freezeAmount = freezeAmount;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	

}
