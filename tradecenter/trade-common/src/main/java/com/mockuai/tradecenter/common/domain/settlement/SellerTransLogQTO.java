package com.mockuai.tradecenter.common.domain.settlement;

import java.util.Date;
import java.util.List;

import com.mockuai.tradecenter.common.domain.BaseQTO;

public class SellerTransLogQTO extends BaseQTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6502678962863968204L;

	private String bizCode;

	private String type;

	private Long sellerId;

	private Long orderId;
	
	private String settlementMark;
	
	private Long withdrawId;
	/**
	 * 开始时间
	 */
	private Date timeStart;

	/**
	 * 结束时间
	 */
	private Date timeEnd;
	
	
	
	private String orderSn;
	
	private List<Long> userIds;


	private Long itemSkuId;
	
	private boolean allNotAvailableTransLog; //所有的不可用余额
	
	private List<Integer> typeIds;
	
	private List<Long> sellerIds;
	
	private Integer mallMark;
	
	private Integer shopType;
	
	
	
	
	
	public Integer getShopType() {
		return shopType;
	}

	public void setShopType(Integer shopType) {
		this.shopType = shopType;
	}

	public Integer getMallMark() {
		return mallMark;
	}

	public void setMallMark(Integer mallMark) {
		this.mallMark = mallMark;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getSettlementMark() {
		return settlementMark;
	}

	public void setSettlementMark(String settlementMark) {
		this.settlementMark = settlementMark;
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


	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<Long> getUserIds() {
		return userIds;
	}
	

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

	public Long getWithdrawId() {
		return withdrawId;
	}

	public void setWithdrawId(Long withdrawId) {
		this.withdrawId = withdrawId;
	}

	public Long getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}

	public boolean isAllNotAvailableTransLog() {
		return allNotAvailableTransLog;
	}

	public void setAllNotAvailableTransLog(boolean allNotAvailableTransLog) {
		this.allNotAvailableTransLog = allNotAvailableTransLog;
	}

	public List<Integer> getTypeIds() {
		return typeIds;
	}

	public void setTypeIds(List<Integer> typeIds) {
		this.typeIds = typeIds;
	}

	public List<Long> getSellerIds() {
		return sellerIds;
	}

	public void setSellerIds(List<Long> sellerIds) {
		this.sellerIds = sellerIds;
	}

	
	

}
