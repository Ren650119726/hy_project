package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yindingyu on 16/5/24.
 */
public class OrderStockDTO implements Serializable{

    /**
     * 多店的卖家id
     */
    private Long sellerId;

    /**
     * 订单流水号
     */
    private String orderSn;

    /**
     * 订单的sku列表
     */
    private List<OrderSku> orderSkuList;

    /**
     * 失败
     */
    private List<OrderSku> failedSkuList;

    public List<OrderSku> getFailedSkuList() {
        return failedSkuList;
    }

    public void setFailedSkuList(List<OrderSku> failedSkuList) {
        this.failedSkuList = failedSkuList;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public List<OrderSku> getOrderSkuList() {
        return orderSkuList;
    }

    public void setOrderSkuList(List<OrderSku> orderSkuList) {
        this.orderSkuList = orderSkuList;
    }

    public static class OrderSku implements Serializable{

        private Long skuId;

        private Integer number;

        private Long storeId;

        private Long supplierId;

        private Long distributorId;

        public Long getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(Long supplierId) {
            this.supplierId = supplierId;
        }

        public Long getSkuId() {
            return skuId;
        }

        public void setSkuId(Long skuId) {
            this.skuId = skuId;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public Long getStoreId() {
            return storeId;
        }

        public void setStoreId(Long storeId) {
            this.storeId = storeId;
        }

        public Long getDistributorId() {
            return distributorId;
        }

        public void setDistributorId(Long distributorId) {
            this.distributorId = distributorId;
        }
    }
}
