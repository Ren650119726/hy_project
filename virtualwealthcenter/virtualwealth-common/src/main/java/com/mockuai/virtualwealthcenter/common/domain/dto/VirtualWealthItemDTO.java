package com.mockuai.virtualwealthcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by duke on 16/4/18.
 */
public class VirtualWealthItemDTO extends BaseDTO implements Serializable {
    /**
     * id
     * */
    private Long id;

    /**
     * 企业标志
     * */
    private String bizCode;

    /**
     * 卖家ID
     * */
    private Long sellerId;

    /**
     * 商品ID
     * */
    private Long itemId;

    /**
     * 商品类型
     * */
    private Integer itemType;

    /**
     * skuId
     * */
    private Long skuId;

    /**
     * 充值面额
     * */
    private Long amount;

    /**
     * 折扣
     * */
    private Double discount;

    private Integer deleteMark;

    private Date gmtCreated;

    private Date gmtModified;
    /**真实姓名**/
    private String  realName ;
    /**银行名称**/
    private String bankName;
    /**银行卡号**/
    private String bankNo;


    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
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

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
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
