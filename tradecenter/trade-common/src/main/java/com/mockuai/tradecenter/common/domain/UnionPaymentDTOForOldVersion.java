package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/6/15.
 */
public class UnionPaymentDTOForOldVersion implements Serializable{
    private String orderId;
    private String reqReserved;
    private String txnAmt;
    private String merId;
    private String respCode;
    private String respMsg;
    private String settleAmt;
    private String queryId;
    private String traceNo;
    private String traceTime;
    /**
     * 透传参数列表，直接透传银联调用的入参列表
     */
    private Map<String,String[]> originParamMap;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReqReserved() {
        return reqReserved;
    }

    public void setReqReserved(String reqReserved) {
        this.reqReserved = reqReserved;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getSettleAmt() {
        return settleAmt;
    }

    public void setSettleAmt(String settleAmt) {
        this.settleAmt = settleAmt;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getTraceTime() {
        return traceTime;
    }

    public void setTraceTime(String traceTime) {
        this.traceTime = traceTime;
    }

    public Map<String, String[]> getOriginParamMap() {
        return originParamMap;
    }

    public void setOriginParamMap(Map<String, String[]> originParamMap) {
        this.originParamMap = originParamMap;
    }
}
