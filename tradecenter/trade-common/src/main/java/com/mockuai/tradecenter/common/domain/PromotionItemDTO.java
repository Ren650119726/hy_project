package com.mockuai.tradecenter.common.domain;


/**
 * 
 * @author cwr
 */
public class PromotionItemDTO {
	
	public Long getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}

	public Boolean getIsGift() {
		return isGift;
	}

	@Override
	public String toString() {
		return "PromotionItemDTO [itemSkuId=" + itemSkuId + ", isGift="
				+ isGift + ",  supplierId=" + supplierId ;
	}

	public void setIsGift(Boolean isGift) {
		this.isGift = isGift;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Long currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Integer getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(Integer promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	
	private Long itemSkuId; // 必须

	private Boolean isGift; // 是否是礼品
	
	private Long itemId; // 商品ID  

	private Long supplierId; // 卖家ID 必须
	
	private Long currentPrice;// 现价

	private Integer promotionPrice;// 促销价 

	private String name;
	
	private Integer number;
	
	private String mainImage;
	
	
	
}
