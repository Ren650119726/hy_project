package com.mockuai.rainbowcenter.common.dto;


/**
 * Created by lizg on 2016/9/27.
 */
public class SyncStockItemSkuDTO extends BaseDTO{


    private Long storeId;

    private Long itemSkuId; //商品sku_id

    private String supplierItmeSkuSn;//商品sku编码

    private Long stockNum;//总库存量

    private Long salesNum;//可销售库存量

    private Long frozenStockNum;//锁定库存量

    private Long soldNum;//预扣库存量


    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getItemSkuId() {
        return itemSkuId;
    }

    public void setItemSkuId(Long itemSkuId) {
        this.itemSkuId = itemSkuId;
    }

    public String getSupplierItmeSkuSn() {
        return supplierItmeSkuSn;
    }

    public void setSupplierItmeSkuSn(String supplierItmeSkuSn) {
        this.supplierItmeSkuSn = supplierItmeSkuSn;
    }

    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    public Long getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(Long salesNum) {
        this.salesNum = salesNum;
    }

    public Long getFrozenStockNum() {
        return frozenStockNum;
    }

    public void setFrozenStockNum(Long frozenStockNum) {
        this.frozenStockNum = frozenStockNum;
    }

    public Long getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(Long soldNum) {
        this.soldNum = soldNum;
    }
}
