package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

public class SkuPropertyValueDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7844856313464049020L;

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getSkuPropertyTmplId() {
		return skuPropertyTmplId;
	}

	public void setSkuPropertyTmplId(Long skuPropertyTmplId) {
		this.skuPropertyTmplId = skuPropertyTmplId;
	}

	private String name;

	private String value;

	private Long skuPropertyTmplId;

	@Override
	public String toString() {
		return "SkuPropertyValueDTO [id=" + id + ", name=" + name
				+ ", value=" + value + ", skuPropertyTmplId="
				+ skuPropertyTmplId + "]";
	}
}
