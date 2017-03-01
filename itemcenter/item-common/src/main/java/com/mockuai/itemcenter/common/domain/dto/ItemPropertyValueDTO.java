package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

public class ItemPropertyValueDTO implements Serializable{

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

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getItemPropertyTmplId() {
		return itemPropertyTmplId;
	}

	public void setItemPropertyTmplId(Long itemPropertyTmplId) {
		this.itemPropertyTmplId = itemPropertyTmplId;
	}

	private String name;

	@Override
	public String toString() {
		return "ItemPropertyValueDTO [id=" + id + ", name=" + name
				+ ", bizCode=" + bizCode + ", value=" + value
				+ ", itemPropertyTmplId=" + itemPropertyTmplId + "]";
	}

	private String bizCode;

	private String value;

	private Long itemPropertyTmplId;
}
