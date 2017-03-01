package com.mockuai.tradecenter.mop.api.domain;

/**
 * Created by zengzhangqiang on 4/27/15.
 */
public class OrderItemUidDTO {
    private Long orderItemId;
    private Long userId;
    private Long sellerId;

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
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