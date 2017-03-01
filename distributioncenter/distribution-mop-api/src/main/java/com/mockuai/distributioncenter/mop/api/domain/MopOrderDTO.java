package com.mockuai.distributioncenter.mop.api.domain;

import java.util.List;

/**
 * Created by duke on 16/5/23.
 */
public class MopOrderDTO {
    private Long orderId;
    private Integer status;
    private String orderSn;
    private List<MopOrderItem> mopOrderItemList;
    private Long realDistAmount;
    private Long virtualDistAmount;
    private String orderTime;

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public List<MopOrderItem> getMopOrderItemList() {
        return mopOrderItemList;
    }

    public void setMopOrderItemList(List<MopOrderItem> mopOrderItemList) {
        this.mopOrderItemList = mopOrderItemList;
    }

    public Long getRealDistAmount() {
        return realDistAmount;
    }

    public void setRealDistAmount(Long realDistAmount) {
        this.realDistAmount = realDistAmount;
    }

    public Long getVirtualDistAmount() {
        return virtualDistAmount;
    }

    public void setVirtualDistAmount(Long virtualDistAmount) {
        this.virtualDistAmount = virtualDistAmount;
    }

    public static class MopOrderItem {
        private Long itemId;
        private Long itemSkuId;
        private String itemSkuDesc;
        private String itemName;
        private Long unitPrice;
        private Integer number;
        private String itemImgUrl;
        private Integer higoMark;

        public Integer getHigoMark() {
            return higoMark;
        }

        public void setHigoMark(Integer higoMark) {
            this.higoMark = higoMark;
        }

        public String getItemImgUrl() {
            return itemImgUrl;
        }

        public void setItemImgUrl(String itemImgUrl) {
            this.itemImgUrl = itemImgUrl;
        }

        public String getItemSkuDesc() {
            return itemSkuDesc;
        }

        public void setItemSkuDesc(String itemSkuDesc) {
            this.itemSkuDesc = itemSkuDesc;
        }

        public Long getItemId() {
            return itemId;
        }

        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        public Long getItemSkuId() {
            return itemSkuId;
        }

        public void setItemSkuId(Long itemSkuId) {
            this.itemSkuId = itemSkuId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public Long getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(Long unitPrice) {
            this.unitPrice = unitPrice;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
    }
}
