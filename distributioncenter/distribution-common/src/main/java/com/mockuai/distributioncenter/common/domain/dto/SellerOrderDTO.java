package com.mockuai.distributioncenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by duke on 16/5/23.
 */
public class SellerOrderDTO implements Serializable {
    private Long orderId;
    private Integer status;
    private String orderSn;
    private List<OrderItem> orderItemList;
    private Long realDistAmount;
    private Long virtualDistAmount;
    private Date orderTime;
    private Date payTime;
    private Date deliveryTime;
    private Date confirmTime;
    private Date shutdownTime;
    private DeliveryInfo deliveryInfo;
    private ConsigneeInfo consigneeInfo;

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

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
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

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Date getShutdownTime() {
        return shutdownTime;
    }

    public void setShutdownTime(Date shutdownTime) {
        this.shutdownTime = shutdownTime;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public ConsigneeInfo getConsigneeInfo() {
        return consigneeInfo;
    }

    public void setConsigneeInfo(ConsigneeInfo consigneeInfo) {
        this.consigneeInfo = consigneeInfo;
    }

    public static class OrderItem implements Serializable {
        private Long itemId;
        private String itemUid;
        private Long itemSkuId;
        private String itemName;
        private String shopName;
        private Long shopId;
        private Long sellerId;
        private String itemImgUrl;
        private String itemSkuDesc;
        private Long unitPrice;
        private Integer number;
        private Integer higoMark;

        public String getItemUid() {
            return itemUid;
        }

        public void setItemUid(String itemUid) {
            this.itemUid = itemUid;
        }

        public Long getSellerId() {
            return sellerId;
        }

        public void setSellerId(Long sellerId) {
            this.sellerId = sellerId;
        }

        public Integer getHigoMark() {
            return higoMark;
        }

        public void setHigoMark(Integer higoMark) {
            this.higoMark = higoMark;
        }

        public Long getItemSkuId() {
            return itemSkuId;
        }

        public void setItemSkuId(Long itemSkuId) {
            this.itemSkuId = itemSkuId;
        }

        public Long getItemId() {
            return itemId;
        }

        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public Long getShopId() {
            return shopId;
        }

        public void setShopId(Long shopId) {
            this.shopId = shopId;
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

    public static class DeliveryInfo implements Serializable {
        private String deliveryCompany;

        public String getDeliveryCompany() {
            return deliveryCompany;
        }

        public void setDeliveryCompany(String deliveryCompany) {
            this.deliveryCompany = deliveryCompany;
        }
    }

    public static class ConsigneeInfo implements Serializable {
        private String provinceName;
        private String cityName;
        private String areaName;
        private String consignee;
        private String mobile;
        private String idCardId;
        private String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getIdCardId() {
            return idCardId;
        }

        public void setIdCardId(String idCardId) {
            this.idCardId = idCardId;
        }
    }
}
