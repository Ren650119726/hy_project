package com.mockuai.usercenter.common.dto;

import java.io.Serializable;
import java.util.Date;

public class UserMessageDTO extends BaseDTO implements Serializable {
	private Long id;
	private String bizCode;
	private Long senderId;
	private Long receiverId;
	private Integer type;
	private Integer sendType;
	private String title;
	private String content;
	private Integer status;
	private Integer deleteMark;
	private Date gmtCreated;
	private String senderName;
	private String receiverName;
	private String time;
	private Long globalId;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverName(){return receiverName;}

	public void setReceiverName(String receiverName){this.receiverName=receiverName;}

	public void setTime(String time){this.time=time;}

	public String getTime(){return  time;}

	public void  setGlobalId(Long globalId){ this.globalId=globalId;}

	public Long getGlobalId(){return  globalId;}
}
