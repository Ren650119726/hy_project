package com.mockuai.tradecenter.common.domain;

public class OrderUidDTO {
    private Long orderId;
    private Long userId;
    private Long sellerId;

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSellerId() {
        return this.sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }
}

/* Location:           /work/tmp/trade-common-0.0.1-20150519.033122-8.jar
 * Qualified Name:     com.mockuai.tradecenter.common.domain.OrderUidDTO
 * JD-Core Version:    0.6.2
 */