package com.mockuai.deliverycenter.mop.api.dto;

import java.util.List;

import com.mockuai.tradecenter.common.domain.OrderItemDTO;

public class MopDeliveryInfoDTO {
	private String deliveryInfoUid;
	private String deliveryCompany;
	private String expressUrl;
	private Integer deliveryType;
	private Long deliveryFee;
	private String deliveryCode;
	private List<MopDeliveryDetailDTO> deliveryDetailList;
	
	
	private List<OrderItemDTO> orderItemList;

	

	public String getExpressUrl() {
		return expressUrl;
	}

	public void setExpressUrl(String expressUrl) {
		this.expressUrl = expressUrl;
	}

	public String getDeliveryInfoUid() {
		return deliveryInfoUid;
	}

	public void setDeliveryInfoUid(String deliveryInfoUid) {
		this.deliveryInfoUid = deliveryInfoUid;
	}

	public String getDeliveryCompany() {
		return deliveryCompany;
	}

	public void setDeliveryCompany(String deliveryCompany) {
		this.deliveryCompany = deliveryCompany;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Long getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(Long deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public List<MopDeliveryDetailDTO> getDeliveryDetailList() {
		return deliveryDetailList;
	}

	public void setDeliveryDetailList(List<MopDeliveryDetailDTO> deliveryDetailList) {
		this.deliveryDetailList = deliveryDetailList;
	}

	public List<OrderItemDTO> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItemDTO> orderItemList) {
		this.orderItemList = orderItemList;
	}

	
}
