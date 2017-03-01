package com.mockuai.tradecenter.common.domain.settlement;

import java.util.Date;
import java.util.List;

import com.mockuai.tradecenter.common.domain.BaseDTO;

public class WithdrawDTO extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1781382318083345458L;
	
	private String bizCode;
	
	private Long sellerId;
	
	private String status;
	
	private String name;
	
	private String account;
	
	private String channel;
	
	private Long withdrawBatchId;
	
	private Long amount;
	
    private Date gmtCreated;

    private Date gmtModified;
    
    private List<Long> withdrawIds;
    
    private Long id;
    
    private Long shopId;
    
    private String shopName;
    
    private String refuseReason;
    
    private Integer auditStatus;
    

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Long getWithdrawBatchId() {
		return withdrawBatchId;
	}

	public void setWithdrawBatchId(Long withdrawBatchId) {
		this.withdrawBatchId = withdrawBatchId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public List<Long> getWithdrawIds() {
		return withdrawIds;
	}

	public void setWithdrawIds(List<Long> withdrawIds) {
		this.withdrawIds = withdrawIds;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	
	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}


	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	

}
