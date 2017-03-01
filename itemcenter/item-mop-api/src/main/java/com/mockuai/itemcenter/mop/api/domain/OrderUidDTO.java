package com.mockuai.itemcenter.mop.api.domain;

/**
 * Created by zengzhangqiang on 4/27/15.
 */
public class OrderUidDTO {
    private Long orderId;
    private Long userId;
    private Long sellerId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }
}
