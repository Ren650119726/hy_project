package com.mockuai.usercenter.common.qto;

import java.io.Serializable;
import java.util.Date;

public class GlobalMessageQTO extends QueryPage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -378148508464643483L;

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

	public String getSendCondition() {
		return sendCondition;
	}

	public void setSendCondition(String sendCondition) {
		this.sendCondition = sendCondition;
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

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	private Long id;
	
	private Long senderId;

	private Integer type;

	private Integer sendType;

	private String sendCondition;

	private String title;

	private String content;

	private Integer status;

	private Date gmtCreated;
	
}
