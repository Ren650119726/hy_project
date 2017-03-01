package com.mockuai.tradecenter.common.domain.settlement;

import com.mockuai.tradecenter.common.domain.BaseDTO;

public class SellerMoneyDTO extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8443075629932670585L;

	private Long sellerId;

	private String bizCode;

	private Long currentBalance;

	private Long canUseBalance;

	private Long freezeAmount;
	
	private Long unSettlementAmount;//未结算金额
	
	private Long availableBalance;
	
	private Long finishedWithdrawAmount;

	private Long processingWithdrawAmount;
	
	private Long mallCommission;//商城佣金
	
	private Long shopIncomeSumAmount;
	
	private Long shopId;
	
	private String shopName;
	
	
	
	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Long getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Long currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Long getCanUseBalance() {
		return canUseBalance;
	}

	public void setCanUseBalance(Long canUseBalance) {
		this.canUseBalance = canUseBalance;
	}

	public Long getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(Long freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	public Long getUnSettlementAmount() {
		return unSettlementAmount;
	}

	public void setUnSettlementAmount(Long unSettlementAmount) {
		this.unSettlementAmount = unSettlementAmount;
	}

	public Long getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Long availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Long getFinishedWithdrawAmount() {
		return finishedWithdrawAmount;
	}

	public void setFinishedWithdrawAmount(Long finishedWithdrawAmount) {
		this.finishedWithdrawAmount = finishedWithdrawAmount;
	}

	public Long getProcessingWithdrawAmount() {
		return processingWithdrawAmount;
	}

	public void setProcessingWithdrawAmount(Long processingWithdrawAmount) {
		this.processingWithdrawAmount = processingWithdrawAmount;
	}

	public Long getMallCommission() {
		return mallCommission;
	}

	public void setMallCommission(Long mallCommission) {
		this.mallCommission = mallCommission;
	}

	public Long getShopIncomeSumAmount() {
		return shopIncomeSumAmount;
	}

	public void setShopIncomeSumAmount(Long shopIncomeSumAmount) {
		this.shopIncomeSumAmount = shopIncomeSumAmount;
	}
	
	
	
	
}
