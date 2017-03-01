package com.mockuai.marketingcenter.common.domain.qto;

import java.io.Serializable;

/**
 * Created by huangsiqian on 2016/10/16.
 */
public class TimePurchaseSKUGoodsQTO implements Serializable {
    private Long skuId;
    private Long goodsQuantity;
    //sku限购数量
    private Long goodsPrice;
    //sku库存
    private Long stockNum;
    private String skuCode;
    private Long promotionPrice;


    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(Long goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    public Long getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Long goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Long promotionPrice) {
        this.promotionPrice = promotionPrice;
    }
}
