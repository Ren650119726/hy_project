package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.Map;


public class AlipaymentDTO implements Serializable {
    private String notifyTime;
    private String notifyType;
    private String notifyId;
    private String signType;
    private String sign;
    /**
     * 商户订单流水号
     */
    private String outTradeNo;
    /**
     * 支付宝交易流水号
     */
    private String tradeNo;
    private String tradeStatus;
    private String totalFee;
    private String isTotalFeeAdjust;
    private String gmtCreate;
    private String gmtPayment;
    private String refundStatus;
    private String gmtRefund;
    private String extraCommonParam;
    private Map<String,String[]> originParamMap;
    
    private String successDetails;
    
    private String failDetails;
    
    private String batchNo;
    
    private String resultDetails;

    public String getNotifyTime() {
        return this.notifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getNotifyType() {
        return this.notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getNotifyId() {
        return this.notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public String getSignType() {
        return this.signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOutTradeNo() {
        return this.outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTradeNo() {
        return this.tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeStatus() {
        return this.tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTotalFee() {
        return this.totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getIsTotalFeeAdjust() {
        return this.isTotalFeeAdjust;
    }

    public void setIsTotalFeeAdjust(String isTotalFeeAdjust) {
        this.isTotalFeeAdjust = isTotalFeeAdjust;
    }

    public String getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtPayment() {
        return this.gmtPayment;
    }

    public void setGmtPayment(String gmtPayment) {
        this.gmtPayment = gmtPayment;
    }

    public String getRefundStatus() {
        return this.refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getGmtRefund() {
        return this.gmtRefund;
    }

    public void setGmtRefund(String gmtRefund) {
        this.gmtRefund = gmtRefund;
    }

    public String getExtraCommonParam() {
        return this.extraCommonParam;
    }

    public void setExtraCommonParam(String extraCommonParam) {
        this.extraCommonParam = extraCommonParam;
    }

    public Map<String, String[]> getOriginParamMap() {
        return originParamMap;
    }

    public void setOriginParamMap(Map<String, String[]> originParamMap) {
        this.originParamMap = originParamMap;
    }

	public String getSuccessDetails() {
		return successDetails;
	}

	public void setSuccessDetails(String successDetails) {
		this.successDetails = successDetails;
	}

	public String getFailDetails() {
		return failDetails;
	}

	public void setFailDetails(String failDetails) {
		this.failDetails = failDetails;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getResultDetails() {
		return resultDetails;
	}

	public void setResultDetails(String resultDetails) {
		this.resultDetails = resultDetails;
	}

	
    
}

/* Location:           /work/tmp/trade-common-0.0.1-20150519.033122-8.jar
 * Qualified Name:     com.mockuai.tradecenter.common.domain.AlipaymentDTO
 * JD-Core Version:    0.6.2
 */