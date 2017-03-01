package com.mockuai.tradecenter.core.domain;

import java.util.Date;

public class OrderLogDO {
    private Long id;

    private String logInfo;

    private Date timeLog;

    private Long orderId;

    private Long userId;

    private Long logAdmin;

    private Integer logType;
    
    private String operator; // 操作人员

    public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo == null ? null : logInfo.trim();
    }

    public Date getTimeLog() {
        return timeLog;
    }

    public void setTimeLog(Date timeLog) {
        this.timeLog = timeLog;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLogAdmin() {
        return logAdmin;
    }

    public void setLogAdmin(Long logAdmin) {
        this.logAdmin = logAdmin;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }
}