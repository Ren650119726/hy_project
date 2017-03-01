package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

	private String skuCode;// sku代号，例如：红色-42码，sku名称为多个不同维度的sku属性的key按一定顺序拼装而成

	private Long itemId; // 商品ID

	private String barCode;// 条码

	private Long sellerId; // 卖家ID

	private Long costPrice;//成本价I

	private Long marketPrice;// 市场价

	private Long promotionPrice;// 促销价

	private Long wirelessPrice;// 无线价

	private Long stockNum;// 库存量

	public Long getFrozenStockNum() {
		return frozenStockNum;
	}

	public void setFrozenStockNum(Long frozenStockNum) {
		this.frozenStockNum = frozenStockNum;
	}

	private Long frozenStockNum;

	private Long soldNum;// 已售数量

	private Integer deleteMark;

	private String bizCode;

	/**
	 * sku关联图片
	 */
	private String imageUrl;
	
	private Date gmtCreated;

	private Date gmtModified;

    private Integer higoMark;//冗余商品的跨境标志，1代表为跨境商品，0代表为非跨境商品

	private List<SkuPropertyDTO> skuPropertyDTOList;

	private SkuHigoExtraInfoDTO skuHigoExtraInfoDTO;

	public SkuHigoExtraInfoDTO getSkuHigoExtraInfoDTO() {
		return skuHigoExtraInfoDTO;
	}

	public void setSkuHigoExtraInfoDTO(SkuHigoExtraInfoDTO skuHigoExtraInfoDTO) {
		this.skuHigoExtraInfoDTO = skuHigoExtraInfoDTO;
	}




	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
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

	public Integer getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public List<SkuPropertyDTO> getSkuPropertyDTOList() {
		return skuPropertyDTOList;
	}

	public void setSkuPropertyDTOList(List<SkuPropertyDTO> skuPropertyDTOList) {
		this.skuPropertyDTOList = skuPropertyDTOList;
	}


    @Override
	public String toString() {
		return "ItemSkuDTO [id=" + id + ", skuCode =" + skuCode + "]";
	}

}