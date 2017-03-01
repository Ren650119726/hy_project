package com.mockuai.tradecenter.common.domain.settlement;

import java.util.Date;
import java.util.List;

import com.mockuai.tradecenter.common.domain.BaseQTO;

public class WithdrawQTO extends BaseQTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6502678962863968204L;

	private String bizCode;

	private String status;

	private Long sellerId;

	/**
	 * 开始时间
	 */
	private Date timeStart;

	/**
	 * 结束时间
	 */
	private Date timeEnd;
	
	private String channel;
	
	private String withdrawBatchId;
	
	private List<Long> ids;
	
	private Long shopId;
	
	private String shopName;
	
	private List<Long> shopIds;
	
	private Integer operByMockuai;
	
	
	public List<Long> getShopIds() {
		return shopIds;
	}

	public void setShopIds(List<Long> shopIds) {
		this.shopIds = shopIds;
	}

	public String getWithdrawBatchId() {
		return withdrawBatchId;
	}

	public void setWithdrawBatchId(String withdrawBatchId) {
		this.withdrawBatchId = withdrawBatchId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getOperByMockuai() {
		return operByMockuai;
	}

	public void setOperByMockuai(Integer operByMockuai) {
		this.operByMockuai = operByMockuai;
	}


	
	

}
