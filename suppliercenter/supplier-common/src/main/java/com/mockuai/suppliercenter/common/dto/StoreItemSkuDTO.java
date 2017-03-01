package com.mockuai.suppliercenter.common.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 仓库和sku关联表
 *
 * @author penghj
 */
public class StoreItemSkuDTO extends BaseDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String bizCode;
    private Long supplierId;//供应商id
    private String supplierName;
    private Long storeId;
    private String storeName;
    private Long itemId;
    private Long itemSkuId;//商品sku_id
    private Long sellerId;//冗余商品的
    private String supplierItmeSkuSn;//供应商商品sku编码
    private String itemName;//商品名稱
    private String iconUrl;//商品圖片
    private Long promotionPrice;// 促销价]
    private Long marketPrice;// 市场价
    private Long stockNum;//总库存量
    private Long salesNum;//可销售库存量
    private Long frozenStockNum;//锁定库存量
    private Long soldNum;//预扣库存量
    private Long version; //版本号
    private Long timestamp; //时间戳
    private Date endTime;//保质期
    private Integer level; //优先级别，1最大
    private Integer status; //仓库状态
    private Integer type;
    private Long deleteVersion;
    private Integer deleteMark;//删除标志位，0代表正常，1代表逻辑删除
    private Date gmtCreated;//数据记录新建时间
    private Date gmtModified;//数据记录最近一次更新时间
    private String mark;
    private String skuCode;//商品规格

    private Long gyerpStockNum;  //管易同步过来的库存量

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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemSkuId() {
        return itemSkuId;
    }

    public void setItemSkuId(Long itemSkuId) {
        this.itemSkuId = itemSkuId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSupplierItmeSkuSn() {
        return supplierItmeSkuSn;
    }

    public void setSupplierItmeSkuSn(String supplierItmeSkuSn) {
        this.supplierItmeSkuSn = supplierItmeSkuSn;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Long getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Long promotionPrice) {
        this.promotionPrice = promotionPrice;
    }


    public Long getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Long marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getDeleteVersion() {
        return deleteVersion;
    }

    public void setDeleteVersion(Long deleteVersion) {
        this.deleteVersion = deleteVersion;
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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Long getGyerpStockNum() {
        return gyerpStockNum;
    }

    public void setGyerpStockNum(Long gyerpStockNum) {
        this.gyerpStockNum = gyerpStockNum;
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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
}
