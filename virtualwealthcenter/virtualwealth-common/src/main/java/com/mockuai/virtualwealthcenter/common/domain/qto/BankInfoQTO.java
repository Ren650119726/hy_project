package com.mockuai.virtualwealthcenter.common.domain.qto;

import java.io.Serializable;
import java.util.Date;

public class BankInfoQTO extends PageQTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6740537070644306408L;
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
	
	private String wdConfigText;
	private Float wdConfigMininum;
	private Float wdConfigMaxnum;
	private Long totalSum;

	public Long getTotalSum() {
		return totalSum;
	}
	public void setTotalSum(Long totalSum) {
		this.totalSum = totalSum;
	}
	public String getWdConfigText() {
		return wdConfigText;
	}
	public void setWdConfigText(String wdConfigText) {
		this.wdConfigText = wdConfigText;
	}
	public Float getWdConfigMininum() {
		return wdConfigMininum;
	}
	public void setWdConfigMininum(Float wdConfigMininum) {
		this.wdConfigMininum = wdConfigMininum;
	}
	public Float getWdConfigMaxnum() {
		return wdConfigMaxnum;
	}
	public void setWdConfigMaxnum(Float wdConfigMaxnum) {
		this.wdConfigMaxnum = wdConfigMaxnum;
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
