package com.mockuai.rainbowcenter.core.service.action.erp.entity;


/**
 * Created by lizg on 2016/6/4.
 * 商品属性
 */
public class ErpItemAttribute{

    private String item_code;   //商品代码

    private String sku_code;  //规格代码

    private String price; //实际单价

    private Integer qty; //商品数量

    private Integer refund; //非退款 ,1退款(退款中);是否退款

    private String note; //备注

    private String oid; //子订单ID

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSku_code() {
        return sku_code;
    }

    public void setSku_code(String sku_code) {
        this.sku_code = sku_code;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public Integer getRefund() {
        return refund;
    }

    public void setRefund(Integer refund) {
        this.refund = refund;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}
