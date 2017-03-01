package com.mockuai.rainbowcenter.core.service.action.erp.entity;

/**
 * Created by lizg on 2016/6/4.
 * 支付信息属性
 */
public class ErpPayments {

    private String pay_type_code;  //支付类型code 是

    private String paytime;  //  支付时间

    private String payment;  //支付金额 是

    private String pay_code; //	支付交易号

    private String account; //支付账户

    public String getPay_type_code() {
        return pay_type_code;
    }

    public void setPay_type_code(String pay_type_code) {
        this.pay_type_code = pay_type_code;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
