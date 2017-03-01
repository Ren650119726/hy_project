package com.mockuai.itemcenter.common.domain.qto;

import java.io.Serializable;

import com.mockuai.itemcenter.common.page.PageInfo;

public class ItemTemplatePropertyQTO extends PageInfo implements Serializable {
	
	private Long id;

	private Long itemTemplateId;// 商品ID

	private Long sellerId;// 供应商ID

	public Long getItemTemplateId() {
		return itemTemplateId;
	}

	public void setItemTemplateId(Long itemTemplateId) {
		this.itemTemplateId = itemTemplateId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSupplierId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}