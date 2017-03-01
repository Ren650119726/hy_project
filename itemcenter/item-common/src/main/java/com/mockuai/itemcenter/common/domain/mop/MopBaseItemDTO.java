package com.mockuai.itemcenter.common.domain.mop;


import java.io.Serializable;
import java.util.List;

/**
 * Created by lizg on 2016/11/6.
 */
public class MopBaseItemDTO  implements Serializable{

    private String itemUid;
    private String itemName;
    private Long sellerId;
    private Long categoryId;
    private Long distributorId;
    private String distributorName;
    private Integer itemType;
    private String iconUrl;
    private String itemDesc;
    private Integer deliveryType;
    private Long freight;
    private Long salesVolume;
    private List<PropertyDTO> bizPropertyList;
    private MopItemBrandDTO itemBrand;
    private List<MopItemImageDTO> itemImageList;
    private List<MopItemSkuDTO> itemSkuList;
    private List<MopSkuPropertyDTO> skuPropertyList;
    private List<MopItemPropertyDTO> itemPropertyList;
    private int virtualMark;
    private String itemGroupUid;
    private String qrCode;
    /**
     *
     * 库存状态 0 售罄  1充足
     */
    private Integer stockStatus;

    public Integer getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(Integer stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public int getVirtualMark() {
        return virtualMark;
    }

    public void setVirtualMark(int virtualMark) {
        this.virtualMark = virtualMark;
    }

    public Long getFreight() {
        return freight;
    }

    public void setFreight(Long freight) {
        this.freight = freight;
    }

    public Long getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Long salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getItemGroupUid() {
        return itemGroupUid;
    }

    public void setItemGroupUid(String itemGroupUid) {
        this.itemGroupUid = itemGroupUid;
    }


    private List<MopValueAddedServiceTypeDTO> serviceTypeList;

    private List<MopItemLabelDTO> itemLabelList;
    private Integer higoMark;
    private MopHigoExtraInfoDTO higoExtraInfo;

    private Integer onSale;

    private String activityIconUrl;

    public Integer getOnSale() {
        return onSale;
    }

    public void setOnSale(Integer onSale) {
        this.onSale = onSale;
    }

    public String getActivityIconUrl() {
        return activityIconUrl;
    }

    public void setActivityIconUrl(String activityIconUrl) {
        this.activityIconUrl = activityIconUrl;
    }

    public List<MopValueAddedServiceTypeDTO> getServiceTypeList() {
        return serviceTypeList;
    }

    public void setServiceTypeList(List<MopValueAddedServiceTypeDTO> serviceTypeList) {
        this.serviceTypeList = serviceTypeList;
    }

    public List<MopItemLabelDTO> getItemLabelList() {
        return itemLabelList;
    }

    public void setItemLabelList(List<MopItemLabelDTO> itemLabelList) {
        this.itemLabelList = itemLabelList;
    }

    private String saleBegin;

    public Object getItemExtraInfo() {
        return itemExtraInfo;
    }

    public void setItemExtraInfo(Object itemExtraInfo) {
        this.itemExtraInfo = itemExtraInfo;
    }

    private String saleEnd;
    private Long saleMinNum;
    private Long saleMaxNum;
    private Integer status;
    private Object itemExtraInfo;
    // 销售剩下的时间,秒
    private Long salesRemainTime;
    private String categoryName;
    /**
     * 角标url
     */
    private String cornerIconUrl;
    /**
     * 货源地
     */
    private String supplyBase;
    // 限购数量;
    private Integer limitBuyCount;
    /**
     * 优惠信息列表
     */
    private List<? extends Object> discountInfoList;

    public String getItemUid() {
        return itemUid;
    }

    public void setItemUid(String itemUid) {
        this.itemUid = itemUid;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getIconUrl() {
        return iconUrl;
    }


    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public List<MopItemImageDTO> getItemImageList() {
        return itemImageList;
    }

    public void setItemImageList(List<MopItemImageDTO> itemImageList) {
        this.itemImageList = itemImageList;
    }

    public List<MopItemSkuDTO> getItemSkuList() {
        return itemSkuList;
    }

    public void setItemSkuList(List<MopItemSkuDTO> itemSkuList) {
        this.itemSkuList = itemSkuList;
    }

    public List<MopSkuPropertyDTO> getSkuPropertyList() {
        return skuPropertyList;
    }

    public void setSkuPropertyList(List<MopSkuPropertyDTO> skuPropertyList) {
        this.skuPropertyList = skuPropertyList;
    }

    public String getSaleBegin() {
        return saleBegin;
    }

    public void setSaleBegin(String saleBegin) {
        this.saleBegin = saleBegin;
    }

    public String getSaleEnd() {
        return saleEnd;
    }

    public void setSaleEnd(String saleEnd) {
        this.saleEnd = saleEnd;
    }

    public MopItemBrandDTO getItemBrand() {
        return itemBrand;
    }

    public void setItemBrand(MopItemBrandDTO itemBrand) {
        this.itemBrand = itemBrand;
    }

    public List<MopItemPropertyDTO> getItemPropertyList() {
        return itemPropertyList;
    }

    public void setItemPropertyList(List<MopItemPropertyDTO> itemPropertyList) {
        this.itemPropertyList = itemPropertyList;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public List<PropertyDTO> getBizPropertyList() {
        return bizPropertyList;
    }

    public void setBizPropertyList(List<PropertyDTO> bizPropertyList) {
        this.bizPropertyList = bizPropertyList;
    }

    public Long getSaleMinNum() {
        return saleMinNum;
    }

    public void setSaleMinNum(Long saleMinNum) {
        this.saleMinNum = saleMinNum;
    }

    public Long getSaleMaxNum() {
        return saleMaxNum;
    }

    public void setSaleMaxNum(Long saleMaxNum) {
        this.saleMaxNum = saleMaxNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getSalesRemainTime() {
        return salesRemainTime;
    }

    public void setSalesRemainTime(Long salesRemainTime) {
        this.salesRemainTime = salesRemainTime;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCornerIconUrl() {
        return cornerIconUrl;
    }

    public void setCornerIconUrl(String cornerIconUrl) {
        this.cornerIconUrl = cornerIconUrl;
    }

    public String getSupplyBase() {
        return supplyBase;
    }

    public void setSupplyBase(String supplyBase) {
        this.supplyBase = supplyBase;
    }

    public Integer getLimitBuyCount() {
        return limitBuyCount;
    }

    public void setLimitBuyCount(Integer limitBuyCount) {
        this.limitBuyCount = limitBuyCount;
    }

    public Integer getHigoMark() {
        return higoMark;
    }

    public void setHigoMark(Integer higoMark) {
        this.higoMark = higoMark;
    }

    public MopHigoExtraInfoDTO getHigoExtraInfo() {
        return higoExtraInfo;
    }

    public void setHigoExtraInfo(MopHigoExtraInfoDTO higoExtraInfo) {
        this.higoExtraInfo = higoExtraInfo;
    }

}
