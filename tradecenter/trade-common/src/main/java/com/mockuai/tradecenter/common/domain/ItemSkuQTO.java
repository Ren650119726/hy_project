package com.mockuai.tradecenter.common.domain;

/**
 * ItemSKU QTO
 * 
 * @author chen.huang
 * @date 2015年1月21日
 */
public class ItemSkuQTO {
	private Integer isDeleted;

	private Long id;

	private Long itemId; // 商品ID

	private Long sellerId;// 卖家ID

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

}