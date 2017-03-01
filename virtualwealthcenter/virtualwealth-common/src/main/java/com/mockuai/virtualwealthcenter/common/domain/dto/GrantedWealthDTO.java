package com.mockuai.virtualwealthcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by edgar.zr on 11/13/15.
 */
public class GrantedWealthDTO implements Serializable {

    private Long id;
    private String bizCode;
    private Long wealthId;
    private Integer wealthType;
    private Integer sourceType;
    /**
     * 标志发放规则主体
     */
    private Long ownerId;
    /**
     * 参与发放判定的数量
     */
    private Long baseAmount;
    private Long amount;
    /**
     * 已使用数量
     */
    private Long usedAmount;
    private Long orderId;
    private String orderSN;
    /**
     * 店铺 id
     */
    private Long shopId;
    /**
     * 商品 id
     */
    private Long itemId;
    /**
     * 商品 id
     */
    private Long skuId;
    /**
     * 店铺名称或商品信息,冗余数据
     */
    private String text;
    /**
     * 发放状态 0:冻结 1:到账(默认) 2:取消 3:过期
     */
    private Integer status;
    private Long receiverId;
    private List<Long> receiverIdList;
    private Long granterId;
    private Date grantedTime;
    private String ruleSnapshot;
    private Integer deleteMark;

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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(Long baseAmount) {
        this.baseAmount = baseAmount;
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

    public List<Long> getReceiverIdList() {
        return receiverIdList;
    }

    public void setReceiverIdList(List<Long> receiverIdList) {
        this.receiverIdList = receiverIdList;
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
}