package com.mockuai.tradecenter.common.domain.settlement;

import com.mockuai.tradecenter.common.domain.BaseDTO;

public class ShopDepositDTO extends BaseDTO{

		/**
	 * 
	 */
	private static final long serialVersionUID = -5874793261208978628L;
		Long sellerId;
		Long amount;
		String bizCode;
		public Long getSellerId() {
			return sellerId;
		}
		public void setSellerId(Long sellerId) {
			this.sellerId = sellerId;
		}
		public Long getAmount() {
			return amount;
		}
		public void setAmount(Long amount) {
			this.amount = amount;
		}
		public String getBizCode() {
			return bizCode;
		}
		public void setBizCode(String bizCode) {
			this.bizCode = bizCode;
		}
		
		
}
