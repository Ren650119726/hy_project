package com.mockuai.tradecenter.common.domain.datamigrate;

import com.mockuai.tradecenter.common.domain.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zengzhangqiang on 7/20/15.
 */
public class MigrateOrderDTO implements Serializable {
    private Long id;

    /**
     * 应用标志码
     */
    private String bizCode;

    /**
     * 订单流水
     */
    private String orderSn;

    /**
     * 订单类型
     */
    private Integer type;

    /**
     * 买家ID
     */
    private Long userId;

    /**
     * 卖家ID
     */
    private Long sellerId;

    /**
     * 商品总价
     */
    private Long totalPrice;

    /**
     * 运费
     */
    private Long deliveryFee;

    /**
     * 订单总金额（商品总价＋总运费-优惠总金额）
     */
    private Long totalAmount;

    /**
     * 优惠标记，0代表没有任何优惠信息，1代表有优惠
     */
    private Integer discountMark;

    /**
     * 总优惠额
     */
    private Long discountAmount;


    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 配送方式ID
     */
    private Integer deliveryId;

    /**
     * 支付方式ID
     */
    private Integer paymentId;

    /**
     * 是否需要发票，0代表不需要，1代表需要
     */
    private Integer invoiceMark;

    /**
     * 删除标记
     */
    private Integer deleteMark;

    /**
     * 买家备注
     */
    private String userMemo;

    /**
     * 卖家备注
     */
    private String sellerMemo;

    /**
     * 管理员备注
     */
    private String adminMemo;

    /**
     * 订单附带信息，由业务接入方自己控制和使用其中的数据，平台只负责透传
     */
    private String attachInfo;
    /**
     * 下单时间
     */
    private Date orderTime;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 发货时间
     */
    private Date consignTime;
    /**
     * 确认收货时间
     */
    private Date receiptTime;

    private Date gmtCreated;

    private Date gmtModified;

    private MigrateOrderConsigneeDTO migrateOrderConsigneeDTO;
    private List<MigrateOrderItemDTO> migrateOrderItemDTOs;
    private MigrateOrderPaymentDTO migrateOrderPaymentDTO;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Long deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getDiscountMark() {
        return discountMark;
    }

    public void setDiscountMark(Integer discountMark) {
        this.discountMark = discountMark;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getInvoiceMark() {
        return invoiceMark;
    }

    public void setInvoiceMark(Integer invoiceMark) {
        this.invoiceMark = invoiceMark;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

    public String getUserMemo() {
        return userMemo;
    }

    public void setUserMemo(String userMemo) {
        this.userMemo = userMemo;
    }

    public String getSellerMemo() {
        return sellerMemo;
    }

    public void setSellerMemo(String sellerMemo) {
        this.sellerMemo = sellerMemo;
    }

    public String getAdminMemo() {
        return adminMemo;
    }

    public void setAdminMemo(String adminMemo) {
        this.adminMemo = adminMemo;
    }

    public String getAttachInfo() {
        return attachInfo;
    }

    public void setAttachInfo(String attachInfo) {
        this.attachInfo = attachInfo;
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

    public Date getConsignTime() {
        return consignTime;
    }

    public void setConsignTime(Date consignTime) {
        this.consignTime = consignTime;
    }

    public Date getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(Date receiptTime) {
        this.receiptTime = receiptTime;
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

    public MigrateOrderConsigneeDTO getMigrateOrderConsigneeDTO() {
        return migrateOrderConsigneeDTO;
    }

    public void setMigrateOrderConsigneeDTO(MigrateOrderConsigneeDTO migrateOrderConsigneeDTO) {
        this.migrateOrderConsigneeDTO = migrateOrderConsigneeDTO;
    }

    public List<MigrateOrderItemDTO> getMigrateOrderItemDTOs() {
        return migrateOrderItemDTOs;
    }

    public void setMigrateOrderItemDTOs(List<MigrateOrderItemDTO> migrateOrderItemDTOs) {
        this.migrateOrderItemDTOs = migrateOrderItemDTOs;
    }

    public MigrateOrderPaymentDTO getMigrateOrderPaymentDTO() {
        return migrateOrderPaymentDTO;
    }

    public void setMigrateOrderPaymentDTO(MigrateOrderPaymentDTO migrateOrderPaymentDTO) {
        this.migrateOrderPaymentDTO = migrateOrderPaymentDTO;
    }
}
