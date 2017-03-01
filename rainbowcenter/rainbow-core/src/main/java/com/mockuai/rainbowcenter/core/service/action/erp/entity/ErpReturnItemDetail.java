package com.mockuai.rainbowcenter.core.service.action.erp.entity;


/**
 * Created by lizg on 2016/6/5.
 * 退货-> 商品属性
 */
public class ErpReturnItemDetail{

    private String barcode;  //商品条码   商品条码与商品代码必选其一

    private String item_code;//商品代码

    private String sku_code; //选择以商品代码方式关联商品时，如果需要关联的商品在系统中存在规格属性，则此字段必填  规格代码

    private Integer qty;   //是  数量

    private Double originPrice; // 标准单价

    private Double price;     //实际单价

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Double originPrice) {
        this.originPrice = originPrice;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getSku_code() {
        return sku_code;
    }

    public void setSku_code(String sku_code) {
        this.sku_code = sku_code;
    }
}
