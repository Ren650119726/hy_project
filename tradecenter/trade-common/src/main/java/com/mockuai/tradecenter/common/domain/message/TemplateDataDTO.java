package com.mockuai.tradecenter.common.domain.message;

import com.mockuai.tradecenter.common.domain.BaseDTO;

public class TemplateDataDTO extends BaseDTO{
	private String value;
	private String color;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
