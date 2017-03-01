package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;

/**
 * 发票的DTO类定义
 * @author Administrator
 *
 */
public class InvoiceDTO implements Serializable {
	private Integer invoiceType;// 发票类型
	
	private Integer invoiceClass;//发票类别(日用品、化妆品)
	
	private String invoiceTitle;//发票抬头
	
	public Integer getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}
	public Integer getInvoiceClass() {
		return invoiceClass;
	}
	public void setInvoiceClass(Integer invoiceClass) {
		this.invoiceClass = invoiceClass;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
}
