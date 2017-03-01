package com.mockuai.virtualwealthcenter.common.domain.dto.mop;

import java.io.Serializable;
import java.util.Date;

public class MopBankInfoAppDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6462662377290520616L;
	private Long id;
	private Long userId;
	private String bizCode;
	private String bankName;
	private String bankLastno;
	private String bankNo;
	private Integer bankType;
	private String bankRemark;
	private Integer bankIsdefault;
	private String bankRealname;
	private Long bankOnedayQuota;
	private Long bankSingleQuota;
	private Integer deleteMark;
	private Date bankBindtime;
	private Date gmtCreated;
	private Date gmtModified;
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
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankLastno() {
		return bankLastno;
	}
	public void setBankLastno(String bankLastno) {
		this.bankLastno = bankLastno;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public Integer getBankType() {
		return bankType;
	}
	public void setBankType(Integer bankType) {
		this.bankType = bankType;
	}
	public String getBankRemark() {
		return bankRemark;
	}
	public void setBankRemark(String bankRemark) {
		this.bankRemark = bankRemark;
	}
	public Integer getBankIsdefault() {
		return bankIsdefault;
	}
	public void setBankIsdefault(Integer bankIsdefault) {
		this.bankIsdefault = bankIsdefault;
	}

	public String getBankRealname() {
		return bankRealname;
	}
	public void setBankRealname(String bankRealname) {
		this.bankRealname = bankRealname;
	}
	public Long getBankOnedayQuota() {
		return bankOnedayQuota;
	}
	public void setBankOnedayQuota(Long bankOnedayQuota) {
		this.bankOnedayQuota = bankOnedayQuota;
	}
	public Long getBankSingleQuota() {
		return bankSingleQuota;
	}
	public void setBankSingleQuota(Long bankSingleQuota) {
		this.bankSingleQuota = bankSingleQuota;
	}
	public Integer getDeleteMark() {
		return deleteMark;
	}
	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}
	public Date getBankBindtime() {
		return bankBindtime;
	}
	public void setBankBindtime(Date bankBindtime) {
		this.bankBindtime = bankBindtime;
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
