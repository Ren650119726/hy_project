package com.mockuai.tradecenter.common.domain;

import java.util.Date;

public class ItemSkuInfo {
	private Long itemSkuId;

	public Long getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
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

	public Long getStockNum() {
		return stockNum;
	}

	public void setStockNum(Long stockNum) {
		this.stockNum = stockNum;
	}

	public Long getSoldNum() {
		return soldNum;
	}

	public void setSoldNum(Long soldNum) {
		this.soldNum = soldNum;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemBriefName() {
		return itemBriefName;
	}

	public void setItemBriefName(String itemBriefName) {
		this.itemBriefName = itemBriefName;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getItemBrandId() {
		return itemBrandId;
	}

	public void setItemBrandId(Integer itemBrandId) {
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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getSpuId() {
		return spuId;
	}

	public void setSpuId(Integer spuId) {
		this.spuId = spuId;
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

	public String getAuditMsg() {
		return auditMsg;
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}

	public Integer getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Integer auditTime) {
		this.auditTime = auditTime;
	}

	public Integer getAuditAdminId() {
		return auditAdminId;
	}

	public void setAuditAdminId(Integer auditAdminId) {
		this.auditAdminId = auditAdminId;
	}

	public Integer getIsSellerPost() {
		return isSellerPost;
	}

	public void setIsSellerPost(Integer isSellerPost) {
		this.isSellerPost = isSellerPost;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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

	public String getBriefIntroduction() {
		return briefIntroduction;
	}

	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}

	private String codeValue;// sku代号，例如：红色-42码，sku名称为多个不同维度的sku属性的key按一定顺序拼装而成

	private Long itemId; // 商品ID

	private String materialCode;// 物料码

	private String barCode;// 条码

	private Long sellerId; // 卖家ID

	private Long marketPrice;// 市场价

	private Long promotionPrice;// 促销价

	private Long wirelessPrice;// 无线价

	private Long stockNum;// 库存量

	private Long soldNum;// 已售数量
	
	private String itemName;// 商品名称

	private String itemBriefName;// 商品简称

	private Long supplierId;// 供应商ID

	private Integer itemBrandId; // 商品品牌ID

	private Integer itemType; // 商品类型：1代表实体商品，2代表虚拟商品

	private String iconUrl; // 商品主图URL

	private Integer categoryId; // 商品所属类目ID

	private Integer spuId; // 所关联的spu属性信息

	private Date saleBegin;// 售卖开始时间

	private Date saleEnd;// 售卖结束时间

	private Integer saleMinNum;// 最小售卖数

	private Integer saleMaxNum;// 最大售卖数

	private Integer itemStatus;// 商品状态

	private String auditMsg;// 审核信息，审核不通过的时候，需要填写

	private Integer auditTime;// 审核时间

	private Integer auditAdminId;// 审核管理员id

	private Integer isSellerPost;// 该商品是否在商家中心增加

	private Integer isDeleted;

	private Date gmtCreated;

	private Date gmtModified;

	private String briefIntroduction;
	
}
