package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.List;

import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;

public class ShopCartDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6438384283540378536L;
	
	/*
	 * 购物车商品列表 
	 */
	private List<CartItemDTO> cartItems;
	
	private String sellerName;
	
	private Long sellerId;
	
	private Long shopId;
	
	List<DiscountInfo> discountInfoList;
	
	

	public List<CartItemDTO> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItemDTO> cartItems) {
		this.cartItems = cartItems;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public List<DiscountInfo> getDiscountInfoList() {
		return discountInfoList;
	}

	public void setDiscountInfoList(List<DiscountInfo> discountInfoList) {
		this.discountInfoList = discountInfoList;
	}

	
	
	

}
