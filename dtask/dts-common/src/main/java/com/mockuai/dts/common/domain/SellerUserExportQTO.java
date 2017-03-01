package com.mockuai.dts.common.domain;

import java.io.Serializable;

import com.mockuai.itemcenter.common.page.PageInfo;
/**
 * 
 * @author liuchao
 *
 */
public class SellerUserExportQTO extends PageInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7945658600833594821L;

	


	private Long userId;
	
	private Long sellerId;
	
	private String userName;
	
	private String mobile;
	
	private String relateStatus;
	
	private String keyword;
	
	private String appKey;
	
	
	

	public String getAppKey() {
		return appKey;
	}


	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}


	public String getKeyword() {
		return keyword;
	}


	public String getRelateStatus() {
		return relateStatus;
	}

	public void setRelateStatus(String relateStatus) {
		this.relateStatus = relateStatus;
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




	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	

	
	
	


}
