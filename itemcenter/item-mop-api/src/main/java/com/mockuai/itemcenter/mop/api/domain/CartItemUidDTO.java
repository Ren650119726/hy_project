package com.mockuai.itemcenter.mop.api.domain;

/**
 * Created by zengzhangqiang on 4/30/15.
 */
public class CartItemUidDTO {
    private long cartItemId;
    private long userId;

    public long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
