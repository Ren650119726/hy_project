package com.mockuai.suppliercenter.common.constant;


/**
 * @author penghj
 */
public enum SupplierOrderStockStatusEnum {


    LOCK(1, "冻结"),

    UNLOCK(2, "解冻"),

    REMOVE(3, "下单减掉库存"),

    RETURN(4, "退单回补库存"),
    
    REREDUCE(5, "支付预扣库存"),
    
    BACKREDUCE(6, "退款退货返库存"),
    
    REALREDUCE(7, "物流发货实扣库存"),;

    //1冻结、2解冻、3、下单减掉库存 4、退单回补库存  SupplierOrderStockQTO

    private int status;

    private String statusName;


    SupplierOrderStockStatusEnum(int status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

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
}
