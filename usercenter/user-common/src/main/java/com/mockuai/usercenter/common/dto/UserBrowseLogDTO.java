package com.mockuai.usercenter.common.dto;

import java.io.Serializable;
import java.util.Date;

public class UserBrowseLogDTO extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long browseLogId;
	private Long userId;
	private String nickName;
	private Long storeId;
	private Long goodsId;
	private Date gmtCreated;
	private Date gmtModified;
	private Integer deleteMark;
	private Long sellerUserId;
	
	public Long getBrowseLogId() {
		return browseLogId;
	}
	public void setBrowseLogId(Long browseLogId) {
		this.browseLogId = browseLogId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Long getStoreId() {
		return storeId;
	}
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
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
	public Integer getDeleteMark() {
		return deleteMark;
	}
	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getSellerUserId() {
		return sellerUserId;
	}
	public void setSellerUserId(Long sellerUserId) {
		this.sellerUserId = sellerUserId;
	}
}
