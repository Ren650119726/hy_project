package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;

/**
 * 商品增值服务价格
 * Created by edgar.zr on 11/30/15.
 */
public class MarketValueAddedServiceDTO implements Serializable {

    private Long id;
    private Long sellerId;
    private String name;
    private Long price;

    public MarketValueAddedServiceDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}