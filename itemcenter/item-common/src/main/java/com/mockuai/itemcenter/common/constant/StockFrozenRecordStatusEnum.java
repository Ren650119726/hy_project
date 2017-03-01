package com.mockuai.itemcenter.common.constant;

/**
 * Created by yindingyu on 16/5/24.
 */
public enum StockFrozenRecordStatusEnum {

    FROZEN(1,"冻结"),
    THAWY(0,"解冻"),
    CRUSHED(2,"已减"),
    RESUME(3,"取消订单补库存");

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

    StockFrozenRecordStatusEnum(int status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }
}
