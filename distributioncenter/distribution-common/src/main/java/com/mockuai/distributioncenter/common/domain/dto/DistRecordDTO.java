package com.mockuai.distributioncenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by duke on 16/3/11.
 */
public class DistRecordDTO implements Serializable {
    /**
     * 主键
     * */
    private Long id;

    /**
     * 状态
     * */
    private Integer status;

    /**
     * 分拥类型
     * */
    private Integer type;

    /**
     * 订单ID
     * */
    private Long orderId;

    /**
     * 订单号
     * */
    private String orderSn;

    /**
     * 商品ID
     * */
    private Long itemId;

    /**
     * sku ID
     * */
    private Long itemSkuId;

    /**
     * 商品单价
     * */
    private Long unitPrice;

    /**
     * 购买商品数量
     * */
    private Integer number;

    /**
     * 分拥来源
     * */
    private Integer source;

    /**
     * 买家ID
     * */
    private Long buyerId;

    /**
     * 卖家ID
     * */
    private Long sellerId;

    /**
     * 店铺ID
     * */
    private Long shopId;

    /**
     * 分拥金额
     * */
    private Long distAmount;

    /**
     * 分拥比率
     * */
    private Double distRatio;
    
    private Double gainsRatio;
    
    private Integer deleteMark;

    private Date gmtCreated;

    private Date gmtModified;

    private Long xiaoshou1;
    private Long xiaoshou2;
    private Long tuandui1;
    private Long tuandui2;
    private Long tuandui3;
    private Long tuandui4;
    
    

	public Double getGainsRatio() {
		return gainsRatio;
	}

	public void setGainsRatio(Double gainsRatio) {
		this.gainsRatio = gainsRatio;
	}

	public Long getXiaoshou1() {
		return xiaoshou1;
	}

	public void setXiaoshou1(Long xiaoshou1) {
		this.xiaoshou1 = xiaoshou1;
	}

	public Long getXiaoshou2() {
		return xiaoshou2;
	}

	public void setXiaoshou2(Long xiaoshou2) {
		this.xiaoshou2 = xiaoshou2;
	}

	public Long getTuandui1() {
		return tuandui1;
	}

	public void setTuandui1(Long tuandui1) {
		this.tuandui1 = tuandui1;
	}

	public Long getTuandui2() {
		return tuandui2;
	}

	public void setTuandui2(Long tuandui2) {
		this.tuandui2 = tuandui2;
	}

	public Long getTuandui3() {
		return tuandui3;
	}

	public void setTuandui3(Long tuandui3) {
		this.tuandui3 = tuandui3;
	}

	public Long getTuandui4() {
		return tuandui4;
	}

	public void setTuandui4(Long tuandui4) {
		this.tuandui4 = tuandui4;
	}

	public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getDistAmount() {
        return distAmount;
    }

    public void setDistAmount(Long distAmount) {
        this.distAmount = distAmount;
    }

    public Double getDistRatio() {
        return distRatio;
    }

    public void setDistRatio(Double distRatio) {
        this.distRatio = distRatio;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
