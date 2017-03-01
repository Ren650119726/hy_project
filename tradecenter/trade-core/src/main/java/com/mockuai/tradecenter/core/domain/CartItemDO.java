package com.mockuai.tradecenter.core.domain;

import java.util.Date;

public class CartItemDO {
	private Long id;
	private String bizCode;
	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 商品ID
	 */
	private Long itemId;
	/**
	 * 商品名称
	 */
	private String itemName;
	/**
	 * 商品主图URL
	 */
	private String itemImageUrl;
	/**
	 * 商品SKU ID
	 */
	private Long itemSkuId;
	/**
	 * 规格描述
	 */
	private String itemSkuDesc;
	/**
	 * 商品卖家ID
	 */
	private Long sellerId;
	/**
	 * 商品原价
	 */
	private Long marketPrice;
	/**
	 * 商品现价
	 */
	private Long promotionPrice;
	/**
	 * 商品无线价格
	 */
	private Long wirelessPrice;

	/**
	 * 商品的发货方式
	 */
	private Integer deliveryType;
	/**
	 * 下单数量
	 */
	private Integer number;
	/**
	 * 逻辑删除标记
	 */
	private Integer deleteMark;
	private Date gmtCreated;
	private Date gmtModified;

	private Integer itemType;

	private Long originalId;
	
	private Long supplierId;//供应商ID

	/**
	 * 分销商id，代表该商品从哪个分销商那里购买的
	 */
	private Long distributorId;

	/**
	 * 分销商店铺名称
	 */
	private String distributorName;

    private Long shareUserId;


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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemImageUrl() {
		return itemImageUrl;
	}

	public void setItemImageUrl(String itemImageUrl) {
		this.itemImageUrl = itemImageUrl;
	}

	public Long getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}

	public String getItemSkuDesc() {
		return itemSkuDesc;
	}

	public void setItemSkuDesc(String itemSkuDesc) {
		this.itemSkuDesc = itemSkuDesc;
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

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
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

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Long getOriginalId() {
		return originalId;
	}

	public void setOriginalId(Long originalId) {
		this.originalId = originalId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
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

	public void setDistributorName(String distributorShopName) {
		this.distributorName = distributorShopName;
	}

    public Long getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(Long shareUserId) {
        this.shareUserId = shareUserId;
    }
}