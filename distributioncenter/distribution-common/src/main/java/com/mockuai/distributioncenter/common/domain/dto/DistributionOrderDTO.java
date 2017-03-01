package com.mockuai.distributioncenter.common.domain.dto;

import java.util.List;

/**
 * Created by duke on 16/5/16.
 */
public class DistributionOrderDTO {
    private Long orderId;
    private String orderSn;
    private Long userId;
    private Long sellerId;
    private Long shopId;
    private int source;
    private int distLevel;
    private List<DistributionItemDTO> itemDTOs;
    private String appKey;
    private Integer paymentId;
    
    
    

	

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public int getDistLevel() {
        return distLevel;
    }

    public void setDistLevel(int distLevel) {
        this.distLevel = distLevel;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public List<DistributionItemDTO> getItemDTOs() {
        return itemDTOs;
    }

    public void setItemDTOs(List<DistributionItemDTO> itemDTOs) {
        this.itemDTOs = itemDTOs;
    }
}
