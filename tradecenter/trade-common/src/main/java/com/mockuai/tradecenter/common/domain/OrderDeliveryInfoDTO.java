package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zengzhangqiang on 5/29/15.
 */
public class OrderDeliveryInfoDTO implements Serializable {
	private String order_sn;
    private Long deliveryInfoId;
    private Long orderId;
    private Long userId;
    private List<Long> skuIdList;
    private Integer deliveryType;
    private String deliveryCompany;
    private Long deliveryFee;
    private String deliveryCode;
    private List<DeliveryDetailDTO> deliveryDetailDTOs;

    private String expressCode;
    
    private String pickupCode;//提货码
    
    private List<Long> orderItemIds;
    
    
    
    
    
    public List<Long> getOrderItemIds() {
		return orderItemIds;
	}

	public void setOrderItemIds(List<Long> orderItemIds) {
		this.orderItemIds = orderItemIds;
	}

	public List<Long> getSkuIdList() {
		return skuIdList;
	}

	public void setSkuIdList(List<Long> skuIdList) {
		this.skuIdList = skuIdList;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}
    public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public Long getDeliveryInfoId() {
        return deliveryInfoId;
    }

    public void setDeliveryInfoId(Long deliveryInfoId) {
        this.deliveryInfoId = deliveryInfoId;
    }

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

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public Long getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Long deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public List<DeliveryDetailDTO> getDeliveryDetailDTOs() {
        return deliveryDetailDTOs;
    }

    public void setDeliveryDetailDTOs(List<DeliveryDetailDTO> deliveryDetailDTOs) {
        this.deliveryDetailDTOs = deliveryDetailDTOs;
    }

	public String getPickupCode() {
		return pickupCode;
	}

	public void setPickupCode(String pickupCode) {
		this.pickupCode = pickupCode;
	}

	@Override
	public String toString() {
		return "OrderDeliveryInfoDTO [order_sn=" + order_sn
				+ ", deliveryInfoId=" + deliveryInfoId + ", orderId=" + orderId
				+ ", userId=" + userId + ", skuIdList=" + skuIdList
				+ ", deliveryType=" + deliveryType + ", deliveryCompany="
				+ deliveryCompany + ", deliveryFee=" + deliveryFee
				+ ", deliveryCode=" + deliveryCode + ", deliveryDetailDTOs="
				+ deliveryDetailDTOs + ", expressCode=" + expressCode
				+ ", pickupCode=" + pickupCode + ", orderItemIds="
				+ orderItemIds + "]";
	}
    
    
}
