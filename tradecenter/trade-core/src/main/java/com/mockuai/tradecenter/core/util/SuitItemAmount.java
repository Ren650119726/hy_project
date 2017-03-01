package com.mockuai.tradecenter.core.util;

public class SuitItemAmount {
	private Long suitPaymentAmount;

	private Long suitPointAmount;

	private Long suitDiscountAmount;

	private Long balancePaymentAmount;

	private Long balancePointAmount;

	private Long balanceDiscountAmount;

	long orderPaymentAmt;

	long orderPointAmt;

	long orderDiscountAmt;

	long orderItemUnitPrice;

	public SuitItemAmount(long orderPaymentAmt, long orderPointAmt, long orderDiscountAmt, long orderItemUnitPrice) {
		this.orderPaymentAmt = orderPaymentAmt;
		this.orderPointAmt = orderPointAmt;
		this.orderDiscountAmt = orderDiscountAmt;
		this.orderItemUnitPrice = orderItemUnitPrice;
	}
	
	
	public void jisuan(){
		
		SuitBalanceAmount payment_sba = new SuitBalanceAmount(orderPaymentAmt,orderItemUnitPrice);
		suitPaymentAmount = payment_sba.getSuitAmount();
		balancePaymentAmount = payment_sba.getBalanceOrderAmount();
		orderItemUnitPrice = payment_sba.getOrderItemUnitPrice();
		
		SuitBalanceAmount point_sba = new SuitBalanceAmount(orderPointAmt,orderItemUnitPrice);
		suitPointAmount = point_sba.getSuitAmount();
		balancePointAmount = point_sba.getBalanceOrderAmount();
		orderItemUnitPrice = point_sba.getOrderItemUnitPrice();
		
		
		SuitBalanceAmount discount_sba = new SuitBalanceAmount(orderDiscountAmt,orderItemUnitPrice);
		suitDiscountAmount = discount_sba.getSuitAmount();
		balanceDiscountAmount = discount_sba.getBalanceOrderAmount();
	}
	
	public static class SuitBalanceAmount{
		
		Long suitAmount;
		Long orderAmount;
		Long orderItemUnitPrice;
		Long balanceOrderAmount;
		
		public SuitBalanceAmount(
				Long orderAmount,
				Long orderItemUnitPrice
			){
			this.orderAmount = orderAmount;
			this.orderItemUnitPrice = orderItemUnitPrice;
			
			getSuitItemAmount();
		}
		
		
		public void getSuitItemAmount() {

			if (orderAmount >= orderItemUnitPrice) {

				suitAmount = orderItemUnitPrice;

				balanceOrderAmount = orderAmount - orderItemUnitPrice;

				orderItemUnitPrice = 0L;
			} else {
				suitAmount = orderAmount;
				balanceOrderAmount = 0l;
				orderItemUnitPrice = orderItemUnitPrice - orderAmount;
			}

		}

		public Long getSuitAmount() {
			return suitAmount;
		}

		public void setSuitAmount(Long suitAmount) {
			this.suitAmount = suitAmount;
		}

		public Long getOrderAmount() {
			return orderAmount;
		}

		public void setOrderAmount(Long orderAmount) {
			this.orderAmount = orderAmount;
		}

		public Long getOrderItemUnitPrice() {
			return orderItemUnitPrice;
		}

		public void setOrderItemUnitPrice(Long orderItemUnitPrice) {
			this.orderItemUnitPrice = orderItemUnitPrice;
		}

		public Long getBalanceOrderAmount() {
			return balanceOrderAmount;
		}

		public void setBalanceOrderAmount(Long balanceOrderAmount) {
			this.balanceOrderAmount = balanceOrderAmount;
		}
		
		
		
	}
	

	

	public Long getSuitPaymentAmount() {
		return suitPaymentAmount;
	}

	public void setSuitPaymentAmount(Long suitPaymentAmount) {
		this.suitPaymentAmount = suitPaymentAmount;
	}

	public Long getSuitPointAmount() {
		return suitPointAmount;
	}

	public void setSuitPointAmount(Long suitPointAmount) {
		this.suitPointAmount = suitPointAmount;
	}

	public Long getSuitDiscountAmount() {
		return suitDiscountAmount;
	}

	public void setSuitDiscountAmount(Long suitDiscountAmount) {
		this.suitDiscountAmount = suitDiscountAmount;
	}

	public Long getBalancePaymentAmount() {
		return balancePaymentAmount;
	}

	public void setBalancePaymentAmount(Long balancePaymentAmount) {
		this.balancePaymentAmount = balancePaymentAmount;
	}

	public Long getBalancePointAmount() {
		return balancePointAmount;
	}

	public void setBalancePointAmount(Long balancePointAmount) {
		this.balancePointAmount = balancePointAmount;
	}

	public Long getBalanceDiscountAmount() {
		return balanceDiscountAmount;
	}

	public void setBalanceDiscountAmount(Long balanceDiscountAmount) {
		this.balanceDiscountAmount = balanceDiscountAmount;
	}

	public static void main(String args[]){
		
		long orderPaymentAmt = 20l;
		long orderPointAmt = 6l; 
		long orderDiscountAmt = 24l;
		long orderItemUnitPrice = 26l;
		
		SuitItemAmount suitItemAmount = new SuitItemAmount(orderPaymentAmt,orderPointAmt,orderDiscountAmt,orderItemUnitPrice);
		System.out.println("suitItemAmount before:"+JsonUtil.toJson(suitItemAmount));
		
		suitItemAmount.jisuan();
		
		System.out.println("suitItemAmount after:"+JsonUtil.toJson(suitItemAmount));
	}
	
}
