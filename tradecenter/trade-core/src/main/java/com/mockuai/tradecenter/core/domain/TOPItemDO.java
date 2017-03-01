package com.mockuai.tradecenter.core.domain;

public class TOPItemDO {
	/**
	 *  商品id
	 */
	private long id;
	
	/**
	 * 商品销售量
	 */
	private long sales_volume;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSales_volume() {
		return sales_volume;
	}
	public void setSales_volume(long sales_volume) {
		this.sales_volume = sales_volume;
	}
	
	
}
