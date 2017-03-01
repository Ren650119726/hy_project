package com.mockuai.virtualwealthcenter.common.domain.qto;

import java.io.Serializable;

public class UserAuthonQTO extends PageQTO implements Serializable{
	

	private Long id;
	private Long userId;
	private String bizCode;
	private String authonPersonalid;
	private String authonNo;
	private String authonMobile;
	private String authonRealname;
	private String authonText;
	private Integer authonStatus;
	private Integer deleteMark;
	private String authonBankname;
	private String pictureFront;
	private String pictureBack;


	public String getPictureFront() {
		return pictureFront;
	}
	public void setPictureFront(String pictureFront) {
		this.pictureFront = pictureFront;
	}
	public String getPictureBack() {
		return pictureBack;
	}
	public void setPictureBack(String pictureBack) {
		this.pictureBack = pictureBack;
	}
	public String getAuthonBankname() {
		return authonBankname;
	}
	public void setAuthonBankname(String authonBankname) {
		this.authonBankname = authonBankname;
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
	public String getAuthonPersonalid() {
		return authonPersonalid;
	}
	public void setAuthonPersonalid(String authonPersonalid) {
		this.authonPersonalid = authonPersonalid;
	}
	public String getAuthonNo() {
		return authonNo;
	}
	public void setAuthonNo(String authonNo) {
		this.authonNo = authonNo;
	}
	public String getAuthonMobile() {
		return authonMobile;
	}
	public void setAuthonMobile(String authonMobile) {
		this.authonMobile = authonMobile;
	}
	public String getAuthonRealname() {
		return authonRealname;
	}
	public void setAuthonRealname(String authonRealname) {
		this.authonRealname = authonRealname;
	}
	public String getAuthonText() {
		return authonText;
	}
	public void setAuthonText(String authonText) {
		this.authonText = authonText;
	}
	public Integer getAuthonStatus() {
		return authonStatus;
	}
	public void setAuthonStatus(Integer authonStatus) {
		this.authonStatus = authonStatus;
	}
	
	public Integer getDeleteMark() {
		return deleteMark;
	}
	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}

}
