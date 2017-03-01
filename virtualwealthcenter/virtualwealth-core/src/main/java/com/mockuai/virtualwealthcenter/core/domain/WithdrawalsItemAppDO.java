package com.mockuai.virtualwealthcenter.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WithdrawalsItemAppDO implements Serializable{
       
    /**
	 * 
	 */
	private static final long serialVersionUID = -2105687761993323577L;
	private Long id;
    private Long userId;
    private String bizCode;
    private String withdrawalsNumber;
    private Integer withdrawalsStatus;
    private String withdrawalsNo;
    private Long withdrawalsAmount;
    private Date withdrawalsTime;
    private Integer deleteMark;
    private Date gmtCreated;
    private Date gmtModified;
    
    private Integer withdrawalsType;
    
    private String withdrawalsBanklog;
    private Integer withdrawalsDotype;
    private String withdrawalsRefuse;
    
	public Integer getWithdrawalsType() {
		return withdrawalsType;
	}
	public void setWithdrawalsType(Integer withdrawalsType) {
		this.withdrawalsType = withdrawalsType;
	}
	public String getWithdrawalsBanklog() {
		return withdrawalsBanklog;
	}
	public void setWithdrawalsBanklog(String withdrawalsBanklog) {
		this.withdrawalsBanklog = withdrawalsBanklog;
	}
	public Integer getWithdrawalsDotype() {
		return withdrawalsDotype;
	}
	public void setWithdrawalsDotype(Integer withdrawalsDotype) {
		this.withdrawalsDotype = withdrawalsDotype;
	}
	public String getWithdrawalsRefuse() {
		return withdrawalsRefuse;
	}
	public void setWithdrawalsRefuse(String withdrawalsRefuse) {
		this.withdrawalsRefuse = withdrawalsRefuse;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getWithdrawalsNumber() {
		return withdrawalsNumber;
	}
	public void setWithdrawalsNumber(String withdrawalsNumber) {
		this.withdrawalsNumber = withdrawalsNumber;
	}
	public Integer getWithdrawalsStatus() {
		return withdrawalsStatus;
	}
	public void setWithdrawalsStatus(Integer withdrawalsStatus) {
		this.withdrawalsStatus = withdrawalsStatus;
	}
	public String getWithdrawalsNo() {
		return withdrawalsNo;
	}
	public void setWithdrawalsNo(String withdrawalsNo) {
		this.withdrawalsNo = withdrawalsNo;
	}
	public Long getWithdrawalsAmount() {
		return withdrawalsAmount;
	}
	public void setWithdrawalsAmount(Long withdrawalsAmount) {
		this.withdrawalsAmount = withdrawalsAmount;
	}
	public Date getWithdrawalsTime() {
		return withdrawalsTime;
	}
	public void setWithdrawalsTime(Date withdrawalsTime) {
		this.withdrawalsTime = withdrawalsTime;
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
    
    
    
}
