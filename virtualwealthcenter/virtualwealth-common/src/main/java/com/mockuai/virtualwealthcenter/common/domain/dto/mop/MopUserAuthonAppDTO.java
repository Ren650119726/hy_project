package com.mockuai.virtualwealthcenter.common.domain.dto.mop;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MopUserAuthonAppDTO implements Serializable{


/**
	 * 
	 */
	private static final long serialVersionUID = 6778682973601813528L;
private Long id;
private Long userId;
private List<Long> userIdList;
private String bizCode;
private String authonPersonalid;
private String authonNo;
private String authonMobile;
private String authonRealname;
private String authonText;
private Integer authonStatus;
private Date authonTime;
private Integer deleteMark;
private Date gmtCreated;
private Date gmtModified;
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
public List<Long> getUserIdList() {
	return userIdList;
}
public void setUserIdList(List<Long> userIdList) {
	this.userIdList = userIdList;
}
private String authonBankname;

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
public Date getAuthonTime() {
	return authonTime;
}
public void setAuthonTime(Date authonTime) {
	this.authonTime = authonTime;
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
@Override
public String toString() {
	return "MopUserAuthonAppDTO [id=" + id + ", userId=" + userId
			+ ", userIdList=" + userIdList + ", authonPersonalid="
			+ authonPersonalid + ", authonMobile=" + authonMobile
			+ ", authonRealname=" + authonRealname + ", authonText="
			+ authonText + ", authonStatus=" + authonStatus + ", deleteMark="
			+ deleteMark + ", gmtCreated=" + gmtCreated + ", gmtModified="
			+ gmtModified + "]";
}


}
