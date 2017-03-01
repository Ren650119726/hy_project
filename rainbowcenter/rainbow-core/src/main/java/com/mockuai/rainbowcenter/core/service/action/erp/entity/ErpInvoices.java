package com.mockuai.rainbowcenter.core.service.action.erp.entity;


/**
 * Created by lizg on 2016/6/4.
 * 发票信息属性
 */
public class ErpInvoices{

    private Integer invoice_type;   //是 1-普通发票；2-增值发票 发票类型

    private String invoice_title;  //发票抬头

    private String invoice_content; //发票内容

    private Double invoice_amount; //发票金额


    public Integer getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(Integer invoice_type) {
        this.invoice_type = invoice_type;
    }

    public String getInvoice_title() {
        return invoice_title;
    }

    public void setInvoice_title(String invoice_title) {
        this.invoice_title = invoice_title;
    }

    public String getInvoice_content() {
        return invoice_content;
    }

    public void setInvoice_content(String invoice_content) {
        this.invoice_content = invoice_content;
    }

    public Double getInvoice_amount() {
        return invoice_amount;
    }

    public void setInvoice_amount(Double invoice_amount) {
        this.invoice_amount = invoice_amount;
    }
}
