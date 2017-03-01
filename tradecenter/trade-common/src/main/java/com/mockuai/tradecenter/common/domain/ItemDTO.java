package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ItemDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5099121530065857622L;

	private Long id;

	private String itemName;// 商品名称

	private String itemBriefName;// 商品简称

	private Long supplierId;// 供应商ID

	private Integer itemBrandId; // 商品品牌ID

	private Integer itemType; // 商品类型：1代表实体商品，2代表虚拟商品

	private String iconUrl; // 商品主图URL

	private Integer categoryId; // 商品所属类目ID

	private Integer spuId; // 所关联的spu属性信息

	private Integer marketPrice;// 市场价

	private Integer promotionPrice;// 促销价

	private Integer wirelessPrice;// 无线价

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

	private List<ItemSkuDTO> itemSkuDTOList;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName == null ? null : itemName.trim();
	}

	public String getItemBriefName() {
		return itemBriefName;
	}

	public void setItemBriefName(String itemBriefName) {
		this.itemBriefName = itemBriefName == null ? null : itemBriefName.trim();
	}

	public Integer getItemBrandId() {
		return itemBrandId;
	}

	public void setItemBrandId(Integer itemBrandId) {
		this.itemBrandId = itemBrandId;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl == null ? null : iconUrl.trim();
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

	public Integer getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Integer marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Integer getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(Integer promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public Integer getWirelessPrice() {
		return wirelessPrice;
	}

	public void setWirelessPrice(Integer wirelessPrice) {
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

	public String getAuditMsg() {
		return auditMsg;
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg == null ? null : auditMsg.trim();
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
		this.briefIntroduction = briefIntroduction == null ? null : briefIntroduction.trim();
	}

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

	@Override
	public String toString() {
		return "ItemDTO [id=" + id + ", itemName=" + itemName + "]";
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public List<ItemSkuDTO> getItemSkuDTOList() {
		return itemSkuDTOList;
	}

	public void setItemSkuDTOList(List<ItemSkuDTO> itemSkuDTOList) {
		this.itemSkuDTOList = itemSkuDTOList;
	}

}