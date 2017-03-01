package com.mockuai.distributioncenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yeliming on 16/5/18.
 */
public class SellerLevelApplyDTO implements Serializable {
	
	private Long id;
    private Long applicantId;
    private String  realName;
    private Integer status;
    private Integer type;
    private String source;
    private String reason;
    private Date gmtCreated;
    private Date gmtModified;
    private Integer deleteMark;
    
    private String levelName;
    private Long directCount;
    private Long groupCount;
    
    
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Long getDirectCount() {
		return directCount;
	}
	public void setDirectCount(Long directCount) {
		this.directCount = directCount;
	}
	public Long getGroupCount() {
		return groupCount;
	}
	public void setGroupCount(Long groupCount) {
		this.groupCount = groupCount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(Long applicantId) {
		this.applicantId = applicantId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
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

  
}
