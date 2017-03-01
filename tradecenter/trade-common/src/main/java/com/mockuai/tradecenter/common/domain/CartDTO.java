package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车信息类
 * @author cwr
 */
public class CartDTO implements Serializable {
	
	public List<CartItemDTO> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItemDTO> cartItems) {
		this.cartItems = cartItems;
	}
	public Long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Long getTransportFee() {
		return transportFee;
	}
	public void setTransportFee(Long transportFee) {
		this.transportFee = transportFee;
	}
	public Long getPromotionSave() {
		return promotionSave;
	}
	public void setPromotionSave(Long promotionSave) {
		this.promotionSave = promotionSave;
	}
	public Long getToPayMoney() {
		return toPayMoney;
	}
	public void setToPayMoney(Long toPayMoney) {
		this.toPayMoney = toPayMoney;
	}
	/*
	 * 购物车商品列表 
	 */
	private List<CartItemDTO> cartItems;
	/*
	 * 商品总价
	 */
	private Long totalPrice;
	/*
	 * 运费
	 */
	private Long transportFee;
	/*
	 * 优惠
	 */
	private Long promotionSave; 
	/*
	 * 应付总额 
	 */
	private Long toPayMoney;
	
	@Override
	public String toString() {
		return "CartDTO [cartItems=" + cartItems + ", totalPrice=" + totalPrice
				+ ", transportFee=" + transportFee + ", promotionSave="
				+ promotionSave + ", toPayMoney=" + toPayMoney + "]";
	}
	
}
