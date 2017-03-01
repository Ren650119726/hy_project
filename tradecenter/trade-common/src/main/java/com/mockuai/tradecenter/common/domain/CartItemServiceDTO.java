package com.mockuai.tradecenter.common.domain;

public class CartItemServiceDTO extends BaseDTO {

	private Long cartId;
	private Long sellerId;
	private Long itemSkuId;
	private String serviceName;
	private Long price;
	private String serviceImageUrl;
	
	private Long serviceId;

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getServiceImageUrl() {
		return serviceImageUrl;
	}

	public void setServiceImageUrl(String serviceImageUrl) {
		this.serviceImageUrl = serviceImageUrl;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	
	
	
}
