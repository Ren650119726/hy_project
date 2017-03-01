package com.mockuai.mainweb.common.domain.dto.index;

/**
 * Created by edgar.zr on 3/28/2016.
 */
public class IndexProductDTO {
    private String text;
    private String imageUrl;
    private String targetUrl;
    private String supplyPlace;
    private Long marketPrice;
    private Long wirelessPrice;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getSupplyPlace() {
        return supplyPlace;
    }

    public void setSupplyPlace(String supplyPlace) {
        this.supplyPlace = supplyPlace;
    }

    public Long getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Long marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Long getWirelessPrice() {
        return wirelessPrice;
    }

    public void setWirelessPrice(Long wirelessPrice) {
        this.wirelessPrice = wirelessPrice;
    }
}