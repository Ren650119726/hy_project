package com.mockuai.tradecenter.common.domain;

import java.util.Date;
import java.util.List;

public class TradeNotifyLogQTO extends BaseQTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8953440002247332266L;

	private String outBillNo;

    private  Integer type;

    private Integer status ;

    private  Long orderId;

    private Integer paymentId;

    private List<Long> orderIds;

    private Long userId ;

    private Date startDate;

    private  Date endDate ;




	public String getOutBillNo() {
		return outBillNo;
	}

	public void setOutBillNo(String outBillNo) {
		this.outBillNo = outBillNo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

    public List<Long> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<Long> orderIds) {
        this.orderIds = orderIds;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }
}
