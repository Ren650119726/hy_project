package com.mockuai.itemcenter.common.constant;

/**
 * Created by yindingyu on 16/5/21.
 */
public enum StockStatus {

    SELL_OUT(0,"售罄"),

    SHORT(1,"紧张"),

    ENOUGH(2,"充足"),;


    private int status;

    private String statusName;

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getStatusName() {
        return statusName;
    }
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    private StockStatus(int status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }
}
