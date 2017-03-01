package com.mockuai.dts.common.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TranslogExportQTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4719833231309351584L;

	private String bizCode;

	private String type;

	private Long sellerId;

	private Long orderId;

	private String settlementMark;

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

	private Integer offset; // 分页起始

	private Integer count; // 分页长度
	
	private Integer shopType;
	
	

	public Integer getShopType() {
		return shopType;
	}

	public void setShopType(Integer shopType) {
		this.shopType = shopType;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

}
