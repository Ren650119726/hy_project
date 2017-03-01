package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by duke on 15/9/23.
 */
public class TransmitDTO implements Serializable {
    /**
     * 编号
     * */
    private Long id;

    /**
     * 用户ID
     * */
    private Long userId;

    /**
     * 企业标识
     * */
    private String bizCode;

    /**
     * 金额比率
     * */
    private Double percent;

    /**
     * 状态
     * */
    private Integer status;

    /**
     * 创建时间
     * */
    private Date gmtCreated;

    /**
     * 修改时间
     * */
    private Date gmtModified;

    /**
     * 删除标识
     * */
    private Integer deleteMark;

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

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
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
