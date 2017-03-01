package com.mockuai.itemcenter.common.domain.qto;

import com.mockuai.itemcenter.common.page.PageInfo;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * TODO qto类需要重构，应该只放支持查询的字段
 */
public class ItemQTO extends PageInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 5458551799337024274L;

    private Long id;

    private Integer lifecycle;

    public Integer getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(Integer lifecycle) {
        this.lifecycle = lifecycle;
    }

    private List<Long> idList;

    private String itemName;// 商品名称

    private Long sellerId;// 供应商ID

    private Long itemBrandId; // 商品品牌ID

    private Integer itemType; // 商品类型：1代表实体商品，2代表虚拟商品

    private List<Integer> itemTypeList ;

    private Long categoryId; // 商品所属类目ID

    private Long marketPrice;// 市场价

    private Long promotionPrice;// 促销价

    private Long wirelessPrice;// 无线价

    private Date saleBegin;// 售卖开始时间

    private Date saleEnd;// 售卖结束bai时间

    private Integer itemStatus;// 商品状态

    private Integer deleteMark;

    private Long shopId;

    private Long groupId;

    private Integer stockStatus;

    private Long saleCommission;

    private Integer higoMark;

    private String supplierItemSkuSn; //商品编码

    private Long parentCategoryId;

    private Boolean isNeedStockNum = true; //是否加载库存信息

    private Boolean isNeedComposite = false ;

    private Integer isNeedQueryRemoveItem  ;


    public List<Integer> getItemTypeList() {
        return itemTypeList;
    }

    public void setItemTypeList(List<Integer> itemTypeList) {
        this.itemTypeList = itemTypeList;
    }

    public String getSupplierItemSkuSn() {
		return supplierItemSkuSn;
	}

	public void setSupplierItemSkuSn(String supplierItemSkuSn) {
		this.supplierItemSkuSn = supplierItemSkuSn;
	}

	public Integer getHigoMark() {
        return higoMark;
    }

    public void setHigoMark(Integer higoMark) {
        this.higoMark = higoMark;
    }

    public Long getSaleCommission() {
        return saleCommission;
    }

    public void setSaleCommission(Long saleCommission) {
        this.saleCommission = saleCommission;
    }

    public Integer getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(Integer stockStatus) {
        this.stockStatus = stockStatus;
    }

    private Integer virtualMark;  //是否需要物流(虚拟商品)

    public Integer getVirtualMark() {
        return virtualMark;
    }

    public void setVirtualMark(Integer virtualMark) {
        this.virtualMark = virtualMark;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    private Date gmtCreated;

    private Date gmtModified;

    private String item_brief;

    private Date createTimeBegin;//复合条件查询时候的录入时间开始

    private Date createTimeEnd;//复合条件查询时候的录入时间结束

    private Long cornerIconId;//角标

    private Integer deliveryType;

    private String bizCode;

    private Long freightTemplate;

    private Long freight;

    private Long weight;

    private Long volume;

    private Long descTmplId;

    private String orderBy;

    private Integer asc;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    private Long supplierId;

    private String commodityCode;

    private Integer shareUserId;


    public Integer getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(Integer shareUserId) {
        this.shareUserId = shareUserId;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getAsc() {
        return asc;
    }

    public void setAsc(Integer asc) {
        this.asc = asc;
    }

    public Long getDescTmplId() {
        return descTmplId;
    }

    public void setDescTmplId(Long descTmplId) {
        this.descTmplId = descTmplId;
    }

    public Long getFreight() {
        return freight;
    }

    public void setFreight(Long freight) {
        this.freight = freight;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Long getFreightTemplate() {
        return freightTemplate;
    }

    public void setFreightTemplate(Long freightTemplate) {
        this.freightTemplate = freightTemplate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
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

    public Long getItemBrandId() {
        return itemBrandId;
    }

    public void setItemBrandId(Long itemBrandId) {
        this.itemBrandId = itemBrandId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Long marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Long getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Long promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Long getWirelessPrice() {
        return wirelessPrice;
    }

    public void setWirelessPrice(Long wirelessPrice) {
        this.wirelessPrice = wirelessPrice;
    }

    public Date getSaleBegin() {
        return saleBegin;
    }

    public void setSaleBegin(Date saleBegin) {
        this.saleBegin = saleBegin;
    }

    public Date getSaleEnd() {
        return saleEnd;
    }

    public void setSaleEnd(Date saleEnd) {
        this.saleEnd = saleEnd;
    }

    public Integer getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Integer itemStatus) {
        this.itemStatus = itemStatus;
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

    public String getItem_brief() {
        return item_brief;
    }

    public void setItem_brief(String item_brief) {
        this.item_brief = item_brief;
    }

    public Date getCreateTimeBegin() {
        return createTimeBegin;
    }

    public void setCreateTimeBegin(Date createTimeBegin) {
        this.createTimeBegin = createTimeBegin;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public Long getCornerIconId() {
        return cornerIconId;
    }

    public void setCornerIconId(Long cornerIconId) {
        this.cornerIconId = cornerIconId;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Boolean getNeedStockNum() {
        return isNeedStockNum;
    }

    public void setNeedStockNum(Boolean needStockNum) {
        isNeedStockNum = needStockNum;
    }

    public Boolean getNeedComposite() {
        return isNeedComposite;
    }

    public void setNeedComposite(Boolean needComposite) {
        isNeedComposite = needComposite;
    }

    public Integer getIsNeedQueryRemoveItem() {
        return isNeedQueryRemoveItem;
    }

    public void setIsNeedQueryRemoveItem(Integer isNeedQueryRemoveItem) {
        this.isNeedQueryRemoveItem = isNeedQueryRemoveItem;
    }
}