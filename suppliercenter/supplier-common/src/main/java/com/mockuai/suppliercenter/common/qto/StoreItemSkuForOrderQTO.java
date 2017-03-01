package com.mockuai.suppliercenter.common.qto;

import java.io.Serializable;
import java.util.List;

public class StoreItemSkuForOrderQTO extends PageInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String bizCode;
    private String supplierName;// 供应商名称
    private String storeName;// 仓库名称

    private List<StoreItme> skuIdList;

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }


    public List<StoreItme> getSkuIdList() {
        return skuIdList;
    }

    public void setSkuIdList(List<StoreItme> skuIdList) {
        this.skuIdList = skuIdList;
    }


    public class StoreItme implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private Long itemSkuId;// 商品sku_id
        private Long orderNum;

        public Long getItemSkuId() {
            return itemSkuId;
        }

        public void setItemSkuId(Long itemSkuId) {
            this.itemSkuId = itemSkuId;
        }

        public Long getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(Long orderNum) {
            this.orderNum = orderNum;
        }


    }

}
