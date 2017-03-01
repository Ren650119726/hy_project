package com.mockuai.usercenter.common.dto;

import java.io.Serializable;
import java.util.Date;

public class SellerUserRelateDTO extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4704846632127726061L;
	
	private Long id;
	
	private String bizCode;
	
	private Long userId;
	
	private Long sellerId;
	
	private Date relateTimes;
	
	private String relateStatus;
	
	private Long finishedOrderNum;
	
	private Long finishedOrderAmount;
	
	private Long refundOrderNum;
	
	private Long cancleOrderNum;
	
	private Long totalOrderNum;
	
	private Long deleteMark;
	
	private Date gmtCreated;
	
	private Date gmtModified;
	
	private String imgUrl;
	
	private String userName;
	
	private String mobile;
	
	

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
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

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Date getRelateTimes() {
		return relateTimes;
	}

	public void setRelateTimes(Date relateTimes) {
		this.relateTimes = relateTimes;
	}

	public String getRelateStatus() {
		return relateStatus;
	}

	public void setRelateStatus(String relateStatus) {
		this.relateStatus = relateStatus;
	}

	public Long getFinishedOrderNum() {
		return finishedOrderNum;
	}

	public void setFinishedOrderNum(Long finishedOrderNum) {
		this.finishedOrderNum = finishedOrderNum;
	}

	public Long getFinishedOrderAmount() {
		return finishedOrderAmount;
	}

	public void setFinishedOrderAmount(Long finishedOrderAmount) {
		this.finishedOrderAmount = finishedOrderAmount;
	}

	public Long getRefundOrderNum() {
		return refundOrderNum;
	}

	public void setRefundOrderNum(Long refundOrderNum) {
		this.refundOrderNum = refundOrderNum;
	}

	public Long getCancleOrderNum() {
		return cancleOrderNum;
	}

	public void setCancleOrderNum(Long cancleOrderNum) {
		this.cancleOrderNum = cancleOrderNum;
	}

	public Long getTotalOrderNum() {
		return totalOrderNum;
	}

	public void setTotalOrderNum(Long totalOrderNum) {
		this.totalOrderNum = totalOrderNum;
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

	public Long getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(Long deleteMark) {
		this.deleteMark = deleteMark;
	}
	
	

	
	
	

}
