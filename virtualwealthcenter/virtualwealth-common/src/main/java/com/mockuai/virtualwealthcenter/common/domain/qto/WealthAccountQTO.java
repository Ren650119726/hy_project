package com.mockuai.virtualwealthcenter.common.domain.qto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by edgar.zr on 11/13/15.
 */
public class WealthAccountQTO extends PageQTO implements Serializable {

    private Long id;
    private List<Long> idList;
    private Long userId;
    private List<Long> userIdList;
    private String bizCode;
    private Integer wealthType;
    /**
     * 帐号数据修改时间区间，开始时间
     */
    private Date startTime;
    /**
     * 帐号数据修改时间区间，结束时间
     */
    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Long> userIdList) {
        this.userIdList = userIdList;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Integer getWealthType() {
        return wealthType;
    }

    public void setWealthType(Integer wealthType) {
        this.wealthType = wealthType;
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
}