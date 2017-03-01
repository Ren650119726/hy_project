package com.mockuai.suppliercenter.core.domain;

import java.util.Date;

/**
 * Created by lizg on 2016/9/23.
 */
public class SupplierStoreItemDO extends BaseDO {

    private Long id;

    private String bizCode;

    private Long itemId;    //商品id

    private String itemName;//商品名称

    private Long sellerId;

    private Long supplierId;//供应商id

    private Long stockNum;//总库存量

    private Long salesNum;//可销售库存量

    private Long frozenStockNum;//锁定库存量

    private Long soldNum;//预扣库存量

    private Long version; //版本号

    private Integer deleteMark;//删除标志位，0代表正常，1代表逻辑删除

    private Date gmtCreated;//数据记录新建时间

    private Date gmtModified;//数据记录最近一次更新时间

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    public Long getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(Long salesNum) {
        this.salesNum = salesNum;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getFrozenStockNum() {
        return frozenStockNum;
    }

    public void setFrozenStockNum(Long frozenStockNum) {
        this.frozenStockNum = frozenStockNum;
    }

    public Long getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(Long soldNum) {
        this.soldNum = soldNum;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
