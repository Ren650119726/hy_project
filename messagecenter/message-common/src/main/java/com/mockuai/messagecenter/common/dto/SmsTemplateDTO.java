package com.mockuai.messagecenter.common.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class SmsTemplateDTO extends BaseDTO implements Serializable {

	private Long id;

	private String tempSn;
	private String tempContent;
	private String tempDesc;
	private Integer deleteMark;
	private Date gmtCreated;
	private Date gmtModified;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTempSn() {
		return tempSn;
	}
	public void setTempSn(String tempSn) {
		this.tempSn = tempSn;
	}
	public String getTempContent() {
		return tempContent;
	}
	public void setTempContent(String tempContent) {
		this.tempContent = tempContent;
	}
	public String getTempDesc() {
		return tempDesc;
	}
	public void setTempDesc(String tempDesc) {
		this.tempDesc = tempDesc;
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
		return "SmsServiceDTO [id=" + id + ", tempSn=" + tempSn
				+ ", tempContent=" + tempContent + ", tempDesc=" + tempDesc
				+ ", deleteMark=" + deleteMark + ", gmtCreated=" + gmtCreated
				+ ", gmtModified=" + gmtModified + "]";
	}


	
	
}
