package com.mockuai.rainbowcenter.common.dto;

import java.util.Date;

/**
 * Created by lizg on 2016/6/15.
 */
public class ErpOrderDTO extends BaseDTO {
    private Long id;
    private String orderId;
    private String gyerpCode;
    private Date createTime;

    private Date gmtCreated;

    private Date gmtModified;

    private String orderSn;

    private Integer deliveryStatus; //-1-发货失败，0-未发货，1-发货中，2-发货成功

    private Integer auditStatus; // 0-未审核 1-已审核

    private Integer storageStatus;  //0 -未入库 1-已入库

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

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getStorageStatus() {
        return storageStatus;
    }

    public void setStorageStatus(Integer storageStatus) {
        this.storageStatus = storageStatus;
    }
}
