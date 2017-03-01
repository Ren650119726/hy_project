package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;

/**
 * ItemSKU DTO
 * 
 * @author chen.huang
 * @date 2015年1月21日
 */
public class ItemSkuDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6900664932001161051L;

	private Long id;

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
	
	private Long itemSkuId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue == null ? null : codeValue.trim();
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode == null ? null : materialCode.trim();
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode == null ? null : barCode.trim();
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

	public void setMarketPrice(Long  marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Long  getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(Long  promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public Long  getWirelessPrice() {
		return wirelessPrice;
	}

	public void setWirelessPrice(Long  wirelessPrice) {
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

	@Override
	public String toString() {
		return "ItemSkuDTO [id=" + id + ", codeValue=" + codeValue + "]";
	}

}