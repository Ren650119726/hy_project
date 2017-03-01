package com.mockuai.tradecenter.common.domain.refund;

import java.util.Date;
import java.util.List;

import com.mockuai.tradecenter.common.domain.BaseDTO;
import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
import com.mockuai.tradecenter.common.domain.OrderDiscountInfoDTO;

public class RefundOrderDTO extends BaseDTO{
	private Long id;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public List<RefundOrderItemDTO> getReturnItems() {
		return returnItems;
	}

	public void setReturnItems(List<RefundOrderItemDTO> returnItems) {
		this.returnItems = returnItems;
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




	public Integer getOrderRefundStatus() {
		return orderRefundStatus;
	}

	public void setOrderRefundStatus(Integer orderRefundStatus) {
		this.orderRefundStatus = orderRefundStatus;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
   
	
	
	
	
	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Long getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(Long deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}





	public OrderConsigneeDTO getOrderConsigneeDTO() {
		return orderConsigneeDTO;
	}

	public void setOrderConsigneeDTO(OrderConsigneeDTO orderConsigneeDTO) {
		this.orderConsigneeDTO = orderConsigneeDTO;
	}





	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}





	public List<OrderDiscountInfoDTO> getOrderDiscountInfoDTOs() {
		return orderDiscountInfoDTOs;
	}

	public void setOrderDiscountInfoDTOs(
			List<OrderDiscountInfoDTO> orderDiscountInfoDTOs) {
		this.orderDiscountInfoDTOs = orderDiscountInfoDTOs;
	}





	private Integer orderRefundStatus;



	private List<RefundOrderItemDTO> returnItems;
	
	private Long orderId;
    
	private Long userId ;
    
    private Long sellerId;
    
    private String orderSn;
    
    private Long discountAmount;
    
    private Long totalAmount;
    
    private Long deliveryFee;
    
    /**
	 * 支付时间
	 */
	private Date payTime;
	
	/**
	 * 订单收货地址信息
	 */
	private OrderConsigneeDTO orderConsigneeDTO;
	
	private Integer paymentId;
	
	private List<OrderDiscountInfoDTO> orderDiscountInfoDTOs;
	
}
