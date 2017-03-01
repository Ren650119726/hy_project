package com.mockuai.rainbowcenter.common.dto;

/**
 * Created by lizg on 2016/9/26.
 */
public class ErpStockDTO extends BaseDTO{

    private String warehouseId;  //仓库id

    private String itemCode;   //商品代码

    private Boolean del;    //是否停用 true-停用；false-未停用

    private String qty;  //库存数

    private String safeQty;  //安全库存

    private String pickQty;  //可配数

    private String salableQty; //可销售数

    private String roadQty;  //在途数

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Boolean getDel() {
        return del;
    }

    public void setDel(Boolean del) {
        this.del = del;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getSafeQty() {
        return safeQty;
    }

    public void setSafeQty(String safeQty) {
        this.safeQty = safeQty;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRoadQty() {
        return roadQty;
    }

    public void setRoadQty(String roadQty) {
        this.roadQty = roadQty;
    }

    public String getPickQty() {
        return pickQty;
    }

    public void setPickQty(String pickQty) {
        this.pickQty = pickQty;
    }

    public String getSalableQty() {
        return salableQty;
    }

    public void setSalableQty(String salableQty) {
        this.salableQty = salableQty;
    }
}
