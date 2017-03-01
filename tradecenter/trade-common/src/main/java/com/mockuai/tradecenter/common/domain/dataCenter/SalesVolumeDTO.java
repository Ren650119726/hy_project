package com.mockuai.tradecenter.common.domain.dataCenter;

import java.io.Serializable;

import com.mockuai.tradecenter.common.domain.ItemDTO;

public class SalesVolumeDTO implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5464424434736021951L;

	/**
	 *商品id 
	 */
	private long item_id;
	
	/**
	 * 商品销售量
	 */
	private long item_sales_volume;
	
	/**
	 * 商品细节
	 */
	private ItemDTO itemDTO = new ItemDTO();
	
	/**
	 * 商品名字
	 */
	private String item_name;
	
	
	
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public long getItem_id() {
		return item_id;
	}
	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}
	public long getItem_sales_volume() {
		return item_sales_volume;
	}
	public void setItem_sales_volume(long item_sales_volume) {
		this.item_sales_volume = item_sales_volume;
	}
	public ItemDTO getItemDTO() {
		return itemDTO;
	}
	public void setItemDTO(ItemDTO item) {
		this.itemDTO = item;
	}
	
	
}
