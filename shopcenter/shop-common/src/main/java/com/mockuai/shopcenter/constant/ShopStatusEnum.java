package com.mockuai.shopcenter.constant;

/**
 * Created by ziqi.
 */
public enum  ShopStatusEnum {

    PENDING_AUDIT(1,"待审核"),

    AUDIT_SUCCESS(2,"审核通过"),

    AUDIT_FAIL(3,"审核不通过"),

    NORMAL_BUSSINESS(4,"正常营业"),

    TEMP_FREEZEN(5,"冻结"),

    CLOSE(6,"关闭");

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

    private ShopStatusEnum(int status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

}
