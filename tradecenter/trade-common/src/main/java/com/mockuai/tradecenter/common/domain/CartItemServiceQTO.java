package com.mockuai.tradecenter.common.domain;

public class CartItemServiceQTO extends BaseQTO{
    private long itemSkuId;
    private long cartId;
	public long getItemSkuId() {
		return itemSkuId;
	}
	public void setItemSkuId(long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}
	public long getCartId() {
		return cartId;
	}
	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

    
    
}

