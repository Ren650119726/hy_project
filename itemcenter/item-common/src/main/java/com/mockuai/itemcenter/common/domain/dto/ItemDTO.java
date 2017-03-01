package com.mockuai.itemcenter.common.domain.dto;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ItemDTO implements Serializable {
	@Override
	public String toString() {
		return "ItemDTO [id=" + id + ", itemName=" + itemName + ", sellerId="
				+ sellerId + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5099121530065857622L;
	
	private Long id;

	private Long distributorId;

	private String distributorName;
	
	private String itemUid;

	private String itemName;// 商品名称

	private String itemUrl;

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    private String itemBrief;

	private Long sellerId;// 店铺用户ID

	private Long supplierId; //供应商id

	private Long score; //商品评分

	private Long itemBrandId; // 商品品牌ID

	private Integer itemType; // 商品类型：1代表实体商品，2代表虚拟商品

	private String iconUrl; // 商品主图URL

	private Long categoryId; // 商品所属类目ID

	private Long groupId; // 商品分组ID;

	private Long costPrice;//成本价

	private Long marketPrice;// 市场价

	private Long promotionPrice;// 促销价

	private Long wirelessPrice;// 无线价

	private Date saleBegin;// 售卖开始时间

	private Date saleEnd;// 售卖结束时间

	private Integer saleMinNum;// 最小售卖数

	private Integer saleMaxNum;// 最大售卖数

	private Integer itemStatus;// 商品状态

    private Integer virtualMark;  //是否需要物流(虚拟商品)

	private String qrCode;

    private Integer stockStatus;

    private Long saleCommission;

	private Long shareUserId;

	private Integer issueStatus;
	
	private String gainPercentDesc;
	private String gainSharingDesc;
	private String sharingGains;

	public String getSharingGains() {
		return sharingGains;
	}

	public void setSharingGains(String sharingGains) {
		this.sharingGains = sharingGains;
	}

	public String getGainSharingDesc() {
		return gainSharingDesc;
	}

	public void setGainSharingDesc(String gainSharingDesc) {
		this.gainSharingDesc = gainSharingDesc;
	}
	


	public String getGainPercentDesc() {
		return gainPercentDesc;
	}

	public void setGainPercentDesc(String gainPercentDesc) {
		this.gainPercentDesc = gainPercentDesc;
	}

	public Integer getIssueStatus() {
		return issueStatus;
	}

	public void setIssueStatus(Integer issueStatus) {
		this.issueStatus = issueStatus;
	}

	public Long getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(Long shareUserId) {
        this.shareUserId = shareUserId;
    }

    public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public Long getSaleCommission() {
        return saleCommission;
    }

    private Object distributorInfoDTO;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Object getDistributorInfoDTO() {
		return distributorInfoDTO;
	}

	public void setDistributorInfoDTO(Object distributorInfoDTO) {
		this.distributorInfoDTO = distributorInfoDTO;
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

    public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

    public Integer getVirtualMark() {
        return virtualMark;
    }

    public void setVirtualMark(Integer virtualMark) {
        this.virtualMark = virtualMark;
    }

	private Integer deleteMark;

	private Date gmtCreated;

	private Date gmtModified;

	private String itemDesc;

    private String beforDesc;

    private String afterDesc;

	private String ShopName;

	private Object itemExtraInfo;

    private Integer singleSku;

	private Long salesVolume;

    private Long descTmplId;

	private String commodityCode;

    private Integer unit;

    private String unitName;

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

    public Long getDescTmplId() {
        return descTmplId;
    }

    public void setDescTmplId(Long descTmplId) {
        this.descTmplId = descTmplId;
    }

	public Long getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Long salesVolume) {
		this.salesVolume = salesVolume;
	}

	public Integer getSingleSku() {
		return singleSku;
    }

    public void setSingleSku(Integer singleSku) {
        this.singleSku = singleSku;
    }

    private Integer higoMark;//商品跨境标志，1代表为跨境商品，0代表为非跨境商品
	private HigoExtraInfoDTO higoExtraInfo;//商品跨境扩展信息

	public String getShopName() {
		return ShopName;
	}

	public void setShopName(String shopName) {
		ShopName = shopName;
	}

	public Object getItemExtraInfo() {
		return itemExtraInfo;
	}

	public void setItemExtraInfo(Object itemExtroInfo) {
		this.itemExtraInfo = itemExtroInfo;
	}

	private List<ItemSkuDTO> itemSkuDTOList;

    private List<ItemLabelDTO> itemLabelDTOList;

    private List<ValueAddedServiceTypeDTO> valueAddedServiceTypeDTOList;

    public List<ItemLabelDTO> getItemLabelDTOList() {
        return itemLabelDTOList;
    }

    public void setItemLabelDTOList(List<ItemLabelDTO> itemLabelDTOList) {
        this.itemLabelDTOList = itemLabelDTOList;
    }

    public List<ValueAddedServiceTypeDTO> getValueAddedServiceTypeDTOList() {
        return valueAddedServiceTypeDTOList;
    }

    public void setValueAddedServiceTypeDTOList(List<ValueAddedServiceTypeDTO> valueAddedServiceTypeDTOList) {
        this.valueAddedServiceTypeDTOList = valueAddedServiceTypeDTOList;
    }

    private List<ItemImageDTO> itemImageDTOList;

	private List<ItemPropertyDTO> itemPropertyList;//普通属性的列表
	private List<SkuPropertyDTO> skuPropertyList;

	private List<ItemDTO> subItemList;

    public List<ItemDTO> getSubItemList() {
        return subItemList;
    }

    public void setSubItemList(List<ItemDTO> subItemList) {
        this.subItemList = subItemList;
    }

    private String categoryName;//类目的中文名称

	private String brandName;//品牌名称

	private String createTime; //特定格式的时间

	private String statusName;//状态名称

	private Long cornerIconId;//角标score

	private String bizCode;

	private SellerBrandDTO itemBrandDTO;

	private Integer deliveryType;//发货方式

	private List<CompositeItemDTO> compositeItemList;//组合商品的列表

    private Boolean isComposite;//是否是组合商品

    private List<LimitEntity> buyLimit;


	private List<? extends Object> discountInfoList;

	private ShopInfoDTO shopInfoDTO;

    public ShopInfoDTO getShopInfoDTO() {
        return shopInfoDTO;
    }

    public void setShopInfoDTO(ShopInfoDTO shopInfoDTO) {
        this.shopInfoDTO = shopInfoDTO;
    }

    private Long freightTemplate;

	private Long freight;

	private Long weight;

	private Long volume;

    private Long shopId;

    private Long stockNum;

	private Long frozenStockNum;

    private Integer bizTag;

	private Integer newSellerWarn;

	private Integer oldBossWarn;
    //收益
    private Long gains ;

	public Long getFrozenStockNum() {
		return frozenStockNum;
	}

	public void setFrozenStockNum(Long frozenStockNum) {
		this.frozenStockNum = frozenStockNum;
	}

	public Integer getNewSellerWarn() {
		return newSellerWarn;
	}

	public void setNewSellerWarn(Integer newSellerWarn) {
		this.newSellerWarn = newSellerWarn;
	}

	public Integer getOldBossWarn() {
		return oldBossWarn;
	}

	public void setOldBossWarn(Integer oldBossWarn) {
		this.oldBossWarn = oldBossWarn;
	}

    private Long categoryTmplId;

	public Long getCategoryTmplId() {
		return categoryTmplId;
    }

    public Integer getBizTag() {
        return bizTag;
    }

    public void setBizTag(Integer bizTag) {
        this.bizTag = bizTag;
    }

    public void setCategoryTmplId(Long categoryTmplId) {
        this.categoryTmplId = categoryTmplId;
    }

    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
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

	public String getItemBrief() {
		return itemBrief;
	}

	public void setItemBrief(String itemBrief) {
		this.itemBrief = itemBrief;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
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

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Long costPrice) {
		this.costPrice = costPrice;
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

	public Integer getSaleMinNum() {
		return saleMinNum;
	}

	public void setSaleMinNum(Integer saleMinNum) {
		this.saleMinNum = saleMinNum;
	}

	public Integer getSaleMaxNum() {
		return saleMaxNum;
	}

	public void setSaleMaxNum(Integer saleMaxNum) {
		this.saleMaxNum = saleMaxNum;
	}

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
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

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public List<ItemSkuDTO> getItemSkuDTOList() {
		return itemSkuDTOList;
	}

	public void setItemSkuDTOList(List<ItemSkuDTO> itemSkuDTOList) {
		this.itemSkuDTOList = itemSkuDTOList;
	}

	public List<ItemImageDTO> getItemImageDTOList() {
		return itemImageDTOList;
	}

	public void setItemImageDTOList(List<ItemImageDTO> itemImageDTOList) {
		this.itemImageDTOList = itemImageDTOList;
	}

	public List<ItemPropertyDTO> getItemPropertyList() {
		return itemPropertyList;
	}

	public void setItemPropertyList(List<ItemPropertyDTO> itemPropertyList) {
		this.itemPropertyList = itemPropertyList;
	}

	public List<SkuPropertyDTO> getSkuPropertyList() {
		return skuPropertyList;
	}

	public void setSkuPropertyList(List<SkuPropertyDTO> skuPropertyList) {
		this.skuPropertyList = skuPropertyList;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Long getCornerIconId() {
		return cornerIconId;
	}

	public void setCornerIconId(Long cornerIconId) {
		this.cornerIconId = cornerIconId;
	}

	public SellerBrandDTO getItemBrandDTO() {
		return itemBrandDTO;
	}

	public void setItemBrandDTO(SellerBrandDTO itemBrandDTO) {
		this.itemBrandDTO = itemBrandDTO;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

    public List<CompositeItemDTO> getCompositeItemList() {
        return compositeItemList;
    }

    public void setCompositeItemList(List<CompositeItemDTO> compositeItemList) {
        this.compositeItemList = compositeItemList;
    }

    public List<LimitEntity> getBuyLimit() {
		return buyLimit;
	}

	public void setBuyLimit(List<LimitEntity> buyLimit) {
		this.buyLimit = buyLimit;
	}

	public List<? extends Object> getDiscountInfoList() {
		return discountInfoList;
	}

    public String getBeforDesc() {
        return beforDesc;
    }

    public void setBeforDesc(String beforDesc) {
        this.beforDesc = beforDesc;
    }

    public String getAfterDesc() {
        return afterDesc;
    }

    public void setAfterDesc(String afterDesc) {
        this.afterDesc = afterDesc;
    }

    public void setDiscountInfoList(List<? extends Object> discountInfoList) {
        this.discountInfoList = discountInfoList;
	}

	public Integer getHigoMark() {
		return higoMark;
	}

	public void setHigoMark(Integer higoMark) {
		this.higoMark = higoMark;
	}

	public HigoExtraInfoDTO getHigoExtraInfo() {
		return higoExtraInfo;
	}

	public void setHigoExtraInfo(HigoExtraInfoDTO higoExtraInfo) {
		this.higoExtraInfo = higoExtraInfo;
	}







	static class DistributorInfo{

		private Long distributorId;

		private String distributorName;

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
	}

    public Long getGains() {
        return gains;
    }

    public void setGains(Long gains) {
        this.gains = gains;
    }

    public Boolean getComposite() {
        return isComposite;
    }

    public void setComposite(Boolean composite) {
        isComposite = composite;
    }
}