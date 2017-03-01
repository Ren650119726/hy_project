package com.mockuai.rainbowcenter.common.dto;

/**
 * Created by lizg on 2016/9/25.
 */
public class SingleSkuSnStockSyncDTO extends BaseDTO{

    private String itemSkuSn;

    private Long storeId;

    private Long itemSkuId;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getItemSkuSn() {
        return itemSkuSn;
    }

    public void setItemSkuSn(String itemSkuSn) {
        this.itemSkuSn = itemSkuSn;
    }

    public Long getItemSkuId() {
        return itemSkuId;
    }

    public void setItemSkuId(Long itemSkuId) {
        this.itemSkuId = itemSkuId;
    }
}
