package com.mockuai.itemcenter.core.domain;

import java.util.Date;

public class ItemDO {


	private Long id;

	private String itemName;// 商品名称

	private String itemBrief;

	private Long sellerId;// 店铺用户ID

	private Long supplierId;  //供应商id

	private Long shopId; //店铺ID

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	private Long itemBrandId; // 商品品牌ID

	private Integer itemType; // 商品类型：1代表实体商品，2代表虚拟商品

	private String iconUrl; // 商品主图URL

	private Long groupId;   // 商品分组ID

	private Long categoryId; // 商品所属类目ID

	private Long costPrice;//成本价

	private Long marketPrice;// 市场价

	private Long promotionPrice;// 促销价

	private Long wirelessPrice;// 无线价

	private Date saleBegin;// 售卖开始时间

	private Date saleEnd;// 售卖结束时间

	private Integer saleMinNum;// 最小售卖数

	private Integer saleMaxNum;// 最大售卖数

	private Integer itemStatus;// 商品状态

	private Long cornerIconId;//角标

	private Integer deliveryType;//发货方式

	private String itemDesc;

	private Integer deleteMark;

	private String bizCode;

	private Date gmtCreated;

	private Date gmtModified;

	private Long freightTemplate;

	private Long freight;

	private Long weight;

	private Long volume;

    private Integer virtualMark;  //是否需要物流(虚拟商品)

    private Long descTmplId;

	private String commodityCode;

	private Long saleCommission;

    private Integer stockStatus;

	private Integer shareUserId;

	private Integer issueStatus;

	public Integer getIssueStatus() {
		return issueStatus;
	}

	public void setIssueStatus(Integer issueStatus) {
		this.issueStatus = issueStatus;
	}

	public Integer getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(Integer shareUserId) {
		this.shareUserId = shareUserId;
	}

	public Integer getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(Integer stockStatus) {
        this.stockStatus = stockStatus;
    }

    public Long getSaleCommission() {
		return saleCommission;
	}

	public void setSaleCommission(Long saleCommission) {
		this.saleCommission = saleCommission;
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

	private Long stockNum;

	private Long frozenStockNum;

    private Integer bizTag;

	private Integer unit;

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public Integer getBizTag() {
        return bizTag;
    }

    public void setBizTag(Integer bizTag) {
        this.bizTag = bizTag;
    }

    public Long getStockNum() {
        return stockNum;
	}

	public void setStockNum(Long stockNum) {
		this.stockNum = stockNum;
	}

	public Long getFrozenStockNum() {
		return frozenStockNum;
	}

	public void setFrozenStockNum(Long frozenStockNum) {
		this.frozenStockNum = frozenStockNum;
	}

	public Long getDescTmplId() {
		return descTmplId;
    }

    public void setDescTmplId(Long descTmplId) {
        this.descTmplId = descTmplId;
    }

    public Integer getVirtualMark() {
        return virtualMark;
    }

    public void setVirtualMark(Integer virtualMark) {
        this.virtualMark = virtualMark;
    }

	private Integer higoMark;//商品跨境标志，1代表跨境商品，0代表非跨境商品

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getCornerIconId() {
		return cornerIconId;
	}

	public void setCornerIconId(Long cornerIconId) {
		this.cornerIconId = cornerIconId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
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

	public Integer getHigoMark() {
		return higoMark;
	}

	public void setHigoMark(Integer higoMark) {
		this.higoMark = higoMark;
	}
}