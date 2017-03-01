package com.mockuai.usercenter.common.qto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserQTO extends BaseQTO implements Serializable {
	private Long id;
	private String bizCode;
	private String name;
	private String email;
	private String mobile;
	private Long inviterId;
	private Long roleMark;
	private Integer status;
	private String invitationCode;
	private Integer sourceType;

	/**
	 * startTime - 时间区间的开始
	 * endTime - 时间区间的结束
	 * */
	private Date startTime;
	private Date endTime;

	private Integer freezStatus;

	private String nickName;

	private List<Long> idList;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getInviterId() {
		return inviterId;
	}

	public void setInviterId(Long inviterId) {
		this.inviterId = inviterId;
	}

	public Long getRoleMark() {
		return roleMark;
	}

	public void setRoleMark(Long roleMark) {
		this.roleMark = roleMark;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public Integer getFreezStatus() {
		return freezStatus;
	}

	public void setFreezStatus(Integer freezStatus) {
		this.freezStatus = freezStatus;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}
}
