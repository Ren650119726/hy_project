package com.mockuai.mainweb.common.domain.dto.publish;

/**
 * Created by Administrator on 2016/9/23.
 */
public class ProductJsonDTO {
    private String imageUrl;

    private String targetUrl;

    private String text;

    private String supplyPlace;

    private Long marketPrice;

    private Long wirelessPrice;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Long marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getSupplyPlace() {
        return supplyPlace;
    }

    public void setSupplyPlace(String supplyPlace) {
        this.supplyPlace = supplyPlace;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getWirelessPrice() {
        return wirelessPrice;
    }

    public void setWirelessPrice(Long wirelessPrice) {
        this.wirelessPrice = wirelessPrice;
    }
}
