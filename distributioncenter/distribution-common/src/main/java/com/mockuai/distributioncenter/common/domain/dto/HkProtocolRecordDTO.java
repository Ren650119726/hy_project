package com.mockuai.distributioncenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lotmac on 16/3/9.
 */
public class HkProtocolRecordDTO implements Serializable {
  	private Long id;

    private Long userId;

    private Long protocolId;

    private String agreeDesc;
    
    private Date agreeTime;

    private Integer appType;
    
    private Integer deleteMark;

    private Date gmtCreated;

    private Date gmtModified;

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

	public String getAgreeDesc() {
		return agreeDesc;
	}

	public void setAgreeDesc(String agreeDesc) {
		this.agreeDesc = agreeDesc;
	}

	public Date getAgreeTime() {
		return agreeTime;
	}

	public void setAgreeTime(Date agreeTime) {
		this.agreeTime = agreeTime;
	}

	public Integer getAppType() {
		return appType;
	}

	public void setAppType(Integer appType) {
		this.appType = appType;
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

    public Long getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(Long protocolId) {
        this.protocolId = protocolId;
    }
}
