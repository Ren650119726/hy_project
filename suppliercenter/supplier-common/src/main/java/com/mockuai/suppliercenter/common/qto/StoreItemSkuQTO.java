package com.mockuai.suppliercenter.common.qto;

import java.io.Serializable;
import java.util.List;

public class StoreItemSkuQTO extends PageInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String bizCode;
    private Long supplierId;//供应商id
    private String supplierName;//供应商名称
    private Long storeId;
    private String storeName;//仓库名称
    private String itemName;//仓库名称

    private Long itemId;
    private Long itemSkuId;//商品sku_id
    private String supplierItmeSkuSn;//供应商商品sku编码

    /**
     * 仓库idList
     */
    private List<Long> storeIdList;
    private List<Long> itemIdList;//商品idlist

    private List<Long> itemSkuIdList; //商品skuIdList

    private List<Long> orItemSkuIdList;

    public List<Long> getStoreIdList() {
        return storeIdList;
    }

    public void setStoreIdList(List<Long> storeIdList) {
        this.storeIdList = storeIdList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public String getSupplierItmeSkuSn() {
        return supplierItmeSkuSn;
    }

    public void setSupplierItmeSkuSn(String supplierItmeSkuSn) {
        this.supplierItmeSkuSn = supplierItmeSkuSn;
    }

	public List<Long> getItemIdList() {
		return itemIdList;
	}

	public void setItemIdList(List<Long> itemIdList) {
		this.itemIdList = itemIdList;
	}

    public List<Long> getItemSkuIdList() {
        return itemSkuIdList;
    }

    public void setItemSkuIdList(List<Long> itemSkuIdList) {
        this.itemSkuIdList = itemSkuIdList;
    }

    public List<Long> getOrItemSkuIdList() {
        return orItemSkuIdList;
    }

    public void setOrItemSkuIdList(List<Long> orItemSkuIdList) {
        this.orItemSkuIdList = orItemSkuIdList;
    }
}
