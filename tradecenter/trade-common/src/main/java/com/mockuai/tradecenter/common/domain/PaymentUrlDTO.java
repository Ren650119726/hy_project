package com.mockuai.tradecenter.common.domain;


import java.io.Serializable;
import java.util.Map;


public class PaymentUrlDTO implements Serializable {
    private static final long serialVersionUID = 1;
    private String payUrl;
    private int payType;
    private int requestMethod;
    private Map<String, String> params;
    private Long payAmount;
    
    private Boolean isPaid;//是否已支付

    public String getPayUrl() {
        return this.payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public int getPayType() {
        return this.payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getRequestMethod() {
        return this.requestMethod;
    }

    public void setRequestMethod(int requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }

	public Boolean isPaid() {
		return isPaid;
	}

	public void setPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}
    
    
}

/* Location:           /work/tmp/trade-common-0.0.1-20150519.033122-8.jar
 * Qualified Name:     com.mockuai.tradecenter.common.domain.PaymentUrlDTO
 * JD-Core Version:    0.6.2
 */