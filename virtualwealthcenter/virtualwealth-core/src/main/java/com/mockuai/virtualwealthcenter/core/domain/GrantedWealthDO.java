package com.mockuai.virtualwealthcenter.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zengzhangqiang on 5/25/15.
 */
public class GrantedWealthDO implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8587234494033325435L;
	private Long id;
    private String bizCode;
    private Long wealthId;
    private Integer wealthType;
    private Integer sourceType;
    private Long amount;
    private Long usedAmount;
    private Long orderId;
    private String orderSN;
    private Long shopId;
    private Long itemId;
    private Long skuId;
    private String text;
    private Integer status;
    private Long receiverId;
    private Long granterId;
    private Date grantedTime;
    private String ruleSnapshot;
    private Integer deleteMark;
    private Date gmtCreated;
    private Date gmtModified;

    private Long overAmount;
    
    public Long getOverAmount() {
		return overAmount;
	}

	public void setOverAmount(Long overAmount) {
		this.overAmount = overAmount;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Long getWealthId() {
        return wealthId;
    }

    public void setWealthId(Long wealthId) {
        this.wealthId = wealthId;
    }

    public Integer getWealthType() {
        return wealthType;
    }

    public void setWealthType(Integer wealthType) {
        this.wealthType = wealthType;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(Long usedAmount) {
        this.usedAmount = usedAmount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderSN() {
        return orderSN;
    }

    public void setOrderSN(String orderSN) {
        this.orderSN = orderSN;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getGranterId() {
        return granterId;
    }

    public void setGranterId(Long granterId) {
        this.granterId = granterId;
    }

    public Date getGrantedTime() {
        return grantedTime;
    }

    public void setGrantedTime(Date grantedTime) {
        this.grantedTime = grantedTime;
    }

    public String getRuleSnapshot() {
        return ruleSnapshot;
    }

    public void setRuleSnapshot(String ruleSnapshot) {
        this.ruleSnapshot = ruleSnapshot;
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