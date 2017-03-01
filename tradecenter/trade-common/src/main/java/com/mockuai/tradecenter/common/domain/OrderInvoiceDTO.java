package com.mockuai.tradecenter.common.domain;

/**
 * Created by zengzhangqiang on 5/24/15.
 */
public class OrderInvoiceDTO {
    private Long id;
    private Long orderId;
    private Long userId;
    /**
     * 发票类型：1个人，2公司
     */
    private Integer invoiceType;
    /**
     * 发票抬头
     */
    private String invoiceTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }
}
