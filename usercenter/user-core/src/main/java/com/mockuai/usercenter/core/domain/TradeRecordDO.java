package com.mockuai.usercenter.core.domain;

import java.util.Date;

/**
 * Created by duke on 15/9/22.
 */
public class TradeRecordDO {

    /**
     * 手机号
     * */
    private String mobile;

    /**
     * ID
     * */
    private Long id;

    /**
     * 用户ID
     * */
    private Long userId;

    /**
     * 联系人姓名
     * */
    private String contactName;

    /**
     * 支付宝交易流水号
     * */
    private String tradeNo;

    /**
     * 套餐类型
     * */
    private Integer type;

    /**
     * 状态
     * */
    private Integer status;

    /**
     * bizCode
     * */
    private String bizCode;

    /**
     * 套餐价格
     * */
    private Double totalFee;

    /**
     * 套餐生效时间
     * */
    private Date gmtValid;

    /**
     * 套餐失效时间
     * */
    private Date gmtInvalid;

    /**
     * 记录创建时间
     * */
    private Date gmtCreated;

    /**
     * 修改时间
     * */
    private Date gmtModified;

    private Integer deleteMark;

    /**
     * 是不是多店铺,0或者null为单店铺,1为多店铺
     */
    private Integer isMultiMall;

    public String getMobile() {
        return mobile;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public Date getGmtValid() {
        return gmtValid;
    }

    public void setGmtValid(Date gmtValid) {
        this.gmtValid = gmtValid;
    }

    public Date getGmtInvalid() {
        return gmtInvalid;
    }

    public void setGmtInvalid(Date gmtInvalid) {
        this.gmtInvalid = gmtInvalid;
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

    public Integer getIsMultiMall() {
        return isMultiMall;
    }

    public void setIsMultiMall(Integer isMultiMall) {
        this.isMultiMall = isMultiMall;
    }
}
