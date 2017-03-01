package com.mockuai.rainbowcenter.core.domain;

import java.util.Date;

/**
 * Created by lizg on 2016/6/15.
 */
public class ErpOrderDO extends BaseDO{
    private Long id;
    private String orderId;
    private String gyerpCode;
    private Date createTime;

    private Integer deleteMark;

    private Date gmtCreated;

    private Date gmtModified;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getGyerpCode() {
        return gyerpCode;
    }

    public void setGyerpCode(String gyerpCode) {
        this.gyerpCode = gyerpCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
}
