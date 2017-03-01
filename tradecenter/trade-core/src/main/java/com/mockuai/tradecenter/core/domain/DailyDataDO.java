package com.mockuai.tradecenter.core.domain;

import java.util.Date;

public class DailyDataDO {
	private Date date;
	private long value;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
	
}
