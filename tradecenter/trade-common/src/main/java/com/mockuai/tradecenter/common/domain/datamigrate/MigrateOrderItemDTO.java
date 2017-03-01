package com.mockuai.tradecenter.common.domain.datamigrate;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zengzhangqiang on 7/20/15.
 */
public class MigrateOrderItemDTO implements Serializable {
    private Long id;
    private String bizCode;
    /**
     * 下单用户ID
     */
    private Long userId;
    /**
     * 下单用户昵称
     */
    private String userName;
    /**
     * 所属订单ID
     */
    private Long orderId;
    /**
     * 商品ID
     */
    private Long itemId;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 商品主图URL
     */
    private String itemImageUrl;
    /**
     * 商品SKU ID
     */
    private Long itemSkuId;
    /**
     * 规格描述
     */
    private String itemSkuDesc;
    /**
     * 商品卖家ID
     */
    private Long sellerId;
    /**
     * 商品单价
     */
    private Long unitPrice;
    /**
     * 商品的发货方式
     */
    private Integer deliveryType;
    /**
     * 下单数量
     */
    private Integer number;
    /**
     * 逻辑删除标记
     */
    private Integer deleteMark;
    private Date gmtCreated;
    private Date gmtModified;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public Long getItemSkuId() {
        return itemSkuId;
    }

    public void setItemSkuId(Long itemSkuId) {
        this.itemSkuId = itemSkuId;
    }

    public String getItemSkuDesc() {
        return itemSkuDesc;
    }

    public void setItemSkuDesc(String itemSkuDesc) {
        this.itemSkuDesc = itemSkuDesc;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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
