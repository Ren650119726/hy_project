package com.mockuai.suppliercenter.common.qto;

import java.io.Serializable;
import java.util.Date;

/**
 * 供应商仓库商品订单sku库存锁定
 *
 * @author penghj
 */
public class SupplierOrderStockQTO extends QueryPage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String bizCode;
    private String orderSn;//订单
    private Long storeId;//仓库id
    private Long itemSkuId;//商品sku_id
    private Long distributorId;//分销店家id
    private Long num;//库存调整
    private Integer status;//1冻结、2解冻、3、下单减掉库存 4、退单回补库存
    private Integer deleteMark;//删除标志位，0代表正常，1代表逻辑删除
    private Date gmtCreated;//数据记录新建时间
    private Date gmtModified;//数据记录最近一次更新时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getItemSkuId() {
        return itemSkuId;
    }

    public void setItemSkuId(Long itemSkuId) {
        this.itemSkuId = itemSkuId;
    }


    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }


}
