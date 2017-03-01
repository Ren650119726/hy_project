package com.mockuai.usercenter.common.qto;

import java.io.Serializable;
import java.util.Date;

public class UserMessageQTO extends QueryPage implements Serializable {
	private Long id;
	private Long senderId;
	private Long receiverId;
	private Integer type;
	private Integer sendType;
	private Integer status;
	private Long globalId;
	private String bizCode;
	private Date userRegDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setGlobalId(Long globalId){  this.globalId=globalId; }

	public Long getGlobalId(){return globalId;}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Date getUserRegDate() {
		return userRegDate;
	}

	public void setUserRegDate(Date userRegDate) {
		this.userRegDate = userRegDate;
	}
}