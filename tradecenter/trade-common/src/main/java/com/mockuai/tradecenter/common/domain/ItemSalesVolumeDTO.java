package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;

public class ItemSalesVolumeDTO implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5265053699262335697L;

	/**
	 *商品id 
	 */
	private long itemId;
	
	/**
	 * 商品销售量
	 */
	private long itemSalesVolume;
	
	/**
	 * 商品名字
	 */
	private String itemName;

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public long getItemSalesVolume() {
		return itemSalesVolume;
	}

	public void setItemSalesVolume(long itemSalesVolume) {
		this.itemSalesVolume = itemSalesVolume;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	
	
	
}
