package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.Date;

public class OrderLogDTO implements Serializable{

	private String logInfo; // 日志内容

    private Date timeLog; // 日志时间
    
    private String operator; // 操作人员
	
    public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public Date getTimeLog() {
		return timeLog;
	}

	public void setTimeLog(Date timeLog) {
		this.timeLog = timeLog;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
