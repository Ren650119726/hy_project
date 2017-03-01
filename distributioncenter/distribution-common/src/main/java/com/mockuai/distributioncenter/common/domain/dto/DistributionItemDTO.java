package com.mockuai.distributioncenter.common.domain.dto;

/**
 * Created by duke on 16/5/16.
 */
public class DistributionItemDTO {
    private Long itemId;
    private Long itemSkuId;
    private String itemName;
    private Integer number;
    private Long unitPrice;
    private Double saleRatio;
    private Long sellerId;

    private Integer source;
    private Long shareUserId;
    
    private Long orderId;
    private String orderSn;
    private Long userId;
    private String appKey;
    
    private Integer distLevel;//1：自购，2：第一笔，3：第er
    
    
    public Integer getDistLevel() {
		return distLevel;
	}

	public void setDistLevel(Integer distLevel) {
		this.distLevel = distLevel;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Long getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(Long shareUserId) {
		this.shareUserId = shareUserId;
	}

	public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Double getSaleRatio() {
        return saleRatio;
    }

    public void setSaleRatio(Double saleRatio) {
        this.saleRatio = saleRatio;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemSkuId() {
        return itemSkuId;
    }

    public void setItemSkuId(Long itemSkuId) {
        this.itemSkuId = itemSkuId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }
}
