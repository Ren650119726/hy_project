package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;


public class OrderStatisticDTO implements Serializable {
    private Integer initialOrderNum;
    private Integer payedOrderNum;
    private Integer deliveredOrderNum;
    private Integer receiptedOrderNum;
    
    private Integer refundingOrderNum;//退款中订单数

    public Integer getInitialOrderNum() {
        return this.initialOrderNum;
    }

    public void setInitialOrderNum(Integer initialOrderNum) {
        this.initialOrderNum = initialOrderNum;
    }

    public Integer getPayedOrderNum() {
        return this.payedOrderNum;
    }

    public void setPayedOrderNum(Integer payedOrderNum) {
        this.payedOrderNum = payedOrderNum;
    }

    public Integer getDeliveredOrderNum() {
        return this.deliveredOrderNum;
    }

    public void setDeliveredOrderNum(Integer deliveredOrderNum) {
        this.deliveredOrderNum = deliveredOrderNum;
    }

    public Integer getReceiptedOrderNum() {
        return this.receiptedOrderNum;
    }

    public void setReceiptedOrderNum(Integer receiptedOrderNum) {
        this.receiptedOrderNum = receiptedOrderNum;
    }

	public Integer getRefundingOrderNum() {
		return refundingOrderNum;
	}

	public void setRefundingOrderNum(Integer refundingOrderNum) {
		this.refundingOrderNum = refundingOrderNum;
	}
    
    
    
}

/* Location:           /work/tmp/trade-common-0.0.1-20150519.033122-8.jar
 * Qualified Name:     com.mockuai.tradecenter.common.domain.OrderStatisticDTO
 * JD-Core Version:    0.6.2
 */