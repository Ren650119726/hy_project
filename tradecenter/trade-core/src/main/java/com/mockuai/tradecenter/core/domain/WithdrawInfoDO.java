package com.mockuai.tradecenter.core.domain;

import java.util.Date;

/**
 * 提现
 *
 */
public class WithdrawInfoDO {

	private Long id;
	
	private String bizCode;
	
	private Long sellerId;
	
	private String status;
	
	private String name;
	
	private String account;
	
	private String channel;
	
	private Long withdrawBatchId;
	
	private Long amount;
	
	 /**
     * 删除标记
     */
    private Integer deleteMark; 
	
    private Date gmtCreated;

    private Date gmtModified;
    
    
    private Long shopId;
    
    private String shopName;
    
    private String refuseReason;
    
    private Date gmtOperate;
    
    private Integer type;
    
    private Integer operByMockuai;
    
    private Integer auditStatus;
    
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
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

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	

	public Date getGmtOperate() {
		return gmtOperate;
	}

	public void setGmtOperate(Date gmtOperate) {
		this.gmtOperate = gmtOperate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOperByMockuai() {
		return operByMockuai;
	}

	public void setOperByMockuai(Integer operByMockuai) {
		this.operByMockuai = operByMockuai;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	

	

	
    
    
}

