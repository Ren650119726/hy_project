package com.mockuai.suppliercenter.common.dto;

import java.io.Serializable;
import java.util.List;

public class SupplierOrderStockDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long sellerId;

    private String bizCode;

    private String orderSn;

    /**
     * 订单的sku列表
     */
    private List<OrderSku> orderSkuList;


    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
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

    public static class OrderSku implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long skuId;

        private Long distributorId;

        private Long supplierId;// 供应商id

        private Integer number;

        private Long storeId;

        public Long getSkuId() {
            return skuId;
        }

        public void setSkuId(Long skuId) {
            this.skuId = skuId;
        }


        public Long getDistributorId() {
            return distributorId;
        }

        public void setDistributorId(Long distributorId) {
            this.distributorId = distributorId;
        }

        public Long getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(Long supplierId) {
            this.supplierId = supplierId;
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
    }

}
