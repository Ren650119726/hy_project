package com.mockuai.distributioncenter.common.domain.dto;

import java.io.Serializable;

/**

/**
 * Created by 冠生 on 2016/5/20.
 */
public class GoodsItemDTO  implements Serializable{
    private Long itemId;
    private String itemName ;
    private String itemUrl;
    private Long promotionPrice;// 促销价
    private String imageUrl; // sku url
    private Long itemBrandId; // 商品品牌ID
    private Long saleDistPrice ;//分佣
    private Double saleDistRatio;//分佣比例
    private String itemUid ;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public Long getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Long promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getItemBrandId() {
        return itemBrandId;
    }

    public void setItemBrandId(Long itemBrandId) {
        this.itemBrandId = itemBrandId;
    }

    public Long getSaleDistPrice() {
        return saleDistPrice;
    }

    public void setSaleDistPrice(Long saleDistPrice) {
        this.saleDistPrice = saleDistPrice;
    }

    public Double getSaleDistRatio() {
        return saleDistRatio;
    }

    public void setSaleDistRatio(Double saleDistRatio) {
        this.saleDistRatio = saleDistRatio;
    }

    public String getItemUid() {
        return itemUid;
    }

    public void setItemUid(String itemUid) {
        this.itemUid = itemUid;
    }
}
