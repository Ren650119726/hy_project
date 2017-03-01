package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;
import java.util.List;

public class MarketItemDTO implements Serializable {
    private static final long serialVersionUID = 6175501392358143432L;
    private String itemName;
    private Long itemId;
    private Long brandId;
    private Long categoryId;
    private Long distributorId;
    private String skuSnapshot;
    /**
     * 默认为最低的 skuId
     */
    private Long itemSkuId;
    private Long sellerId;
    private Integer number;
    private Long unitPrice;
    /**
     * 是否为虚拟商品
     */
    private Integer virtualMark;
    /**
     * 除商品本身价格外的复合价格
     */
    private Long totalPrice;
    /**
     * 是否为跨境信息
     */
    private Integer higoMark;
    /**
     * 用于税费计算
     */
    private Long originTotalPrice;
    private String iconUrl;
    /**
     * 商品所属类型
     */
    private Integer itemType;
    private Long supplierId;
    private Integer deliveryType;
    private List<MarketValueAddedServiceDTO> services;
    private ActivityInfo activityInfo;    
    private HigoExtraInfoDTO higoExtraInfo;//跨境扩展信息
    private DistributorInfoDTO distributorInfoDTO;
    
    private String shareUserId;//分享人id
   
    private Long limitNumber; //商品限购数量 

	/**
	 * 组合商品SKU ID
	 */
	private Long combineItemSkuId;

	public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getItemSkuId() {
        return this.itemSkuId;
    }

    public void setItemSkuId(Long itemSkuId) {
        this.itemSkuId = itemSkuId;
    }

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public String getSkuSnapshot() {
        return skuSnapshot;
    }

    public void setSkuSnapshot(String skuSnapshot) {
        this.skuSnapshot = skuSnapshot;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getHigoMark() {
        return higoMark;
    }

    public void setHigoMark(Integer higoMark) {
        this.higoMark = higoMark;
    }

    public Long getOriginTotalPrice() {
        return originTotalPrice;
    }

    public void setOriginTotalPrice(Long originTotalPrice) {
        this.originTotalPrice = originTotalPrice;
    }

    public Integer getVirtualMark() {
        return virtualMark;
    }

    public void setVirtualMark(Integer virtualMark) {
        this.virtualMark = virtualMark;
    }

    public Long getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getSellerId() {
        return this.sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getItemName() {
        return this.itemName;
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

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public List<MarketValueAddedServiceDTO> getServices() {
        return services;
    }

    public void setServices(List<MarketValueAddedServiceDTO> services) {
        this.services = services;
    }

    public ActivityInfo getActivityInfo() {
        return activityInfo;
    }

    public void setActivityInfo(ActivityInfo activityInfo) {
        this.activityInfo = activityInfo;
    }

    public HigoExtraInfoDTO getHigoExtraInfo() {
        return higoExtraInfo;
    }

    public void setHigoExtraInfo(HigoExtraInfoDTO higoExtraInfo) {
        this.higoExtraInfo = higoExtraInfo;
    }

    public DistributorInfoDTO getDistributorInfoDTO() {
        return distributorInfoDTO;
    }

    public void setDistributorInfoDTO(DistributorInfoDTO distributorInfoDTO) {
        this.distributorInfoDTO = distributorInfoDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarketItemDTO that = (MarketItemDTO) o;

        if (itemId != null ? !itemId.equals(that.itemId) : that.itemId != null) return false;
        if (brandId != null ? !brandId.equals(that.brandId) : that.brandId != null) return false;
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
        return itemSkuId != null ? itemSkuId.equals(that.itemSkuId) : that.itemSkuId == null;

    }

    @Override
    public int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + (brandId != null ? brandId.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (itemSkuId != null ? itemSkuId.hashCode() : 0);
        return result;
    }

	public String getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(String shareUserId) {
		this.shareUserId = shareUserId;
	}

	public Long getLimitNumber() {
		return limitNumber;
	}

	public void setLimitNumber(Long limitNumber) {
		this.limitNumber = limitNumber;
	}

	public Long getCombineItemSkuId() {
		return combineItemSkuId;
	}

	public void setCombineItemSkuId(Long combineItemSkuId) {
		this.combineItemSkuId = combineItemSkuId;
	}
}
