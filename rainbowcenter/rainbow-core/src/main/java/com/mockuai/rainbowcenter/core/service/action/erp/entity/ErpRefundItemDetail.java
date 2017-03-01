package com.mockuai.rainbowcenter.core.service.action.erp.entity;


/**
 * Created by lizg on 2016/6/4.
 * 	退款商品
 */
public class ErpRefundItemDetail{

    private String barcode;  //商品条码

    private Integer qty;  //数量

    private  Double price; //单价

    private String note;   //备注

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
