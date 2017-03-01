package com.mockuai.tradecenter.mop.api.domain;

public class MopSellerDTO {
    private Long sellerId;
    private Integer sellerType;
    private String sellerName;

    public Long getSellerId() {
        return this.sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getSellerType() {
        return this.sellerType;
    }

    public void setSellerType(Integer sellerType) {
        this.sellerType = sellerType;
    }

    public String getSellerName() {
        return this.sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
}

/* Location:           /work/tmp/trade-mop-api-0.0.1-20150519.033139-22.jar
 * Qualified Name:     com.mockuai.tradecenter.mop.api.domain.MopSellerDTO
 * JD-Core Version:    0.6.2
 */