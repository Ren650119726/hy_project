package com.mockuai.tradecenter.core.domain;

import java.util.Date;

public class OrderSeqDO {
    private Integer id; // 主键

    private Date day; // 天
 
    private Integer seq; // 天的流水
    
	private Date gmtCreated; 
    
    private Date gmtModified;
   

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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
}